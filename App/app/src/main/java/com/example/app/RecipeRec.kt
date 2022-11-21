package com.example.app

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.example.app.APIS.SortMyRecipe
import com.example.app.APIS.compare
import com.example.app.APIS.recipe
import com.example.app.APIS.recipeapi
import com.example.app.RecAdapter.EndCook
import com.example.app.RecAdapter.R_ingerdientAdapter
import com.example.app.RecAdapter.minusIngredient
import com.example.app.alert.AlertSetting
import com.example.app.localdb.RoomExpDB
import com.example.app.localdb.RoomHelper
import com.example.app.refrigerator.RefrigeratorStatus
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_recipe_rec.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ThreadLocalRandom

class RecipeRec : AppCompatActivity() {
    val items = mutableListOf<recipe>()
    var howtorecipe = arrayOfNulls<String>(500)
    private var Booleanname: String? = null
    private var KeyAPI: String? = null
    val dbList = mutableListOf<RoomExpDB>()
    lateinit var helper: RoomHelper
    var Extra = mutableListOf<EndCook>()
    var Sort = ThreadLocalRandom.current().nextInt(1,5)
    var AfterFilter = mutableListOf<SortMyRecipe>()
    var used = mutableListOf<minusIngredient>()

    //플레이어
    private var exoPlayer: ExoPlayer? = null
    private lateinit var videoPlayer: PlayerView

    //통신 함수
    var gson = GsonBuilder().setLenient().create()
    val retrofit = Retrofit.Builder()
        .baseUrl("http://jaeryurp.duckdns.org:40131/")
//            .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson)) //있으나마나한 코드...
        .build()

    //재료창 리사이클러뷰
    lateinit var ingredAdapter: R_ingerdientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_rec)
        var require_in = findViewById(R.id.require_in) as TextView
        var recipe_name = findViewById(R.id.name_cook) as TextView
        var recipehow = findViewById<TextView>(R.id.pageview)
        videoPlayer = findViewById(R.id.video_player)

        val rv = findViewById<RecyclerView>(R.id.ingerd_recycler)

        helper = Room.databaseBuilder(baseContext, RoomHelper::class.java, "internalExpDb")
            .build()
        getRoomDb()

        val home = findViewById<ImageView>(R.id.home)
        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val refrigerator = findViewById<ImageView>(R.id.refrigerator)
        refrigerator.setOnClickListener {
            val intent = Intent(this, RefrigeratorStatus::class.java)
            startActivity(intent)
        }

        if (intent.hasExtra("BooleanName")) {
            Booleanname = intent.getStringExtra("BooleanName")
            Log.d("Boolean", "$Booleanname")
        } else {
        }


        if (intent.hasExtra("key"))
            KeyAPI = intent.getStringExtra("key")
        var api = retrofit.create(recipeapi::class.java)

        val callResult: Call<JsonArray>?
        if (KeyAPI == "korea") {
            callResult = api.getKorea()
        } else if (KeyAPI == "japan") {
            callResult = api.getJapan()
        } else if (KeyAPI == "western") {
            callResult = api.getWestern()
        } else if (KeyAPI == "china") {
            callResult = api.getChina()
        } else if (KeyAPI == "bunsik") {
            callResult = api.getSnack()
        } else if (KeyAPI == "meat") {
            callResult = api.getRandom()
        } else if (KeyAPI == "seafood") {
            callResult = api.getRandom()
        } else if (KeyAPI == "random") {
            callResult = api.getRandom()
        } else if (KeyAPI == "image") {
            callResult = api.getRandom()
        } else {
            callResult = api.getRandom()
        }

        var resultJsonArray: JsonArray?
        var recipeto = findViewById<TextView>(R.id.recipe_howto)
        var recipeHowCount = 1

        val back = findViewById<ImageButton>(R.id.back)
        back.setOnClickListener {
            onBackPressed()
        }

        callResult.enqueue(object : Callback<JsonArray> {
            override fun onResponse(
                call: Call<JsonArray>,
                response: Response<JsonArray>
            ) {
                resultJsonArray = response.body()
                val jsonArray = JSONTokener(resultJsonArray.toString()).nextValue() as JSONArray

                insertRecipe(jsonArray) /*json 자료 파싱후 배열에 삽입*/

                filter_recipe() /*필터에 맞춰 순서 정렬*/

                if (Booleanname != null) {
                    for (i in items.indices) {
                        if (items[i].name == Booleanname) {
                            require_in.setText(items[i].Ingredient.toString())
                            recipe_name.setText(items[i].name)
                            Log.d("match", "$items")
                            Imgtorecipe(i)
                            recipeto.setText(howtorecipe[i])
                            recipehow.setText(recipeHowCount.toString())
                            Sort = i
                            playYoutubeLink(items[i].link)

                        }
                    }
                } else {
                    require_in.setText(items[0].Ingredient.toString())
                    recipe_name.setText(items[0].name)
                    Log.d("match", "$items")

                    Imgtorecipe(0)
                    recipeto.setText(howtorecipe[0])
                    recipehow.setText(recipeHowCount.toString())

                    playYoutubeLink(items[0].link)

                }
                MakeExtra(rv)

//                for(i in items[Sort].Ingredient.indices){
//                    val split = items[Sort].Ingredient[i].split(' ')
//                    val ingredient = split[0]
//                    val used = split[1]
//                    Extra.add(EndCook(ingredient,used))
//                }
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("FeatTwo", "실패 : $t")
            }
        })


        val left = findViewById<Button>(R.id.left_btn)
        val right = findViewById<Button>(R.id.right_btn)
        left.setOnClickListener {
            if (recipeHowCount > 1) {
                recipeHowCount--
                recipehow.setText((recipeHowCount).toString())
                recipeto.setText(howtorecipe[recipeHowCount - 1])
            } else Toast.makeText(
                this@RecipeRec,
                "첫번째 페이지 입니다.",
                Toast.LENGTH_SHORT
            ).show()
        }
        right.setOnClickListener {

            if (howtorecipe[recipeHowCount]?.isEmpty() == true) Toast.makeText(
                this@RecipeRec,
                "마지막 페이지 입니다.",
                Toast.LENGTH_SHORT
            ).show()
            else {
                recipeHowCount++
                recipehow.setText((recipeHowCount).toString())
                recipeto.setText(howtorecipe[recipeHowCount - 1])
            }
        }

        Log.d("Have?", "$Extra")
    }

    fun onDialogClicked2(view: View) {
        Log.d("Extra", "$Extra")
        for (i in Extra.indices){
            Extra[i].usedIn = Extra[i].usedIn.replace("[^0-9]".toRegex(),"")
        }
        val check = Check(Extra, this)
        check.show()

        check.setOnCancelListener() {
            Log.d("dialog","die")
            // 식재료 다쓴거 푸시알림.
            sendNotification("요리완료", "사용된 재료가 차감되었습니다")

        }
    }

    //notify
    fun sendNotification(title: String = "", message: String = "", channelId: String= "") {

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
//        val notificationID = Random().nextInt(1000) // 0~999의 정수 랜덤 추출

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager, AlertSetting.CHANNEL_ID)
        }
        //야간설정
        if (title.isNotEmpty() && message.isNotEmpty()) {
            when(title){
                "야간알림설정" ->{
                    //            val intent = Intent(this, RefrigeratorStatus::class.java)
//            val pendingIntent = getActivity(this, 0, intent, FLAG_IMMUTABLE)

                    val notification = NotificationCompat.Builder(this, AlertSetting.CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
                        .build()
                    notificationManager.notify(AlertSetting.notificationID_night, notification)
                }
                "식재료소진알림설정" ->{
                    val notification = NotificationCompat.Builder(this, AlertSetting.CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
                        .build()
                    notificationManager.notify(AlertSetting.notificationID_NO_ITEM, notification)
                }
                "유통기한임박알림설정"->{
                    val notification = NotificationCompat.Builder(this, AlertSetting.CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
                        .build()
                    notificationManager.notify(AlertSetting.notificationID_EXP, notification)
                }
                "전체알림설정"->{
                    val notification = NotificationCompat.Builder(this, AlertSetting.CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
                        .build()
                    notificationManager.notify(AlertSetting.notificationID_ALL, notification)
                }
                else -> {
                    val notification = NotificationCompat.Builder(this, AlertSetting.CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
                        .build()
                    notificationManager.notify(AlertSetting.notificationID_ALL, notification)
                }
            }

        } else {

            val notification = NotificationCompat.Builder(this, AlertSetting.CHANNEL_ID)
                .setContentTitle("전체 알람 켜짐")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .build()
//            (System.currentTimeMillis()).toInt()
            // noti 쌓기
//            notificationManager.notify((System.currentTimeMillis()).toInt(), notification)

            notificationManager.notify(AlertSetting.notificationID_night, notification)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        notificationManager: NotificationManager,
        channelId: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                channelId,
                AlertSetting.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel Description"
                enableLights(true)
                lightColor = Color.GREEN
            }
            notificationManager.createNotificationChannel(channel)
        }
    }



    fun MakeExtra(rv: RecyclerView) {
        for (i in items[Sort].Ingredient.indices) {
            val split = items[Sort].Ingredient[i].split(' ')
            val ingredient = split[0]
            val used = split[1]
            Extra.add(EndCook(ingredient, used))
        }
        ingredAdapter = R_ingerdientAdapter(Extra)
        rv.adapter = ingredAdapter
        rv.layoutManager = GridLayoutManager(this, 3)

    }


    fun getRoomDb() {
        if (dbList.isNotEmpty()) {
            dbList.clear()
        }
        CoroutineScope(Dispatchers.IO).launch {
            //room에 있는 데이터 가져오기
            dbList.clear()
            dbList.addAll(helper.roomExpDao().getAll())
            //RoomDb가 존재하지 않으면 build하도록
            if (dbList.size == 0) {
                //냉장고 리스트에 내용물 없을 때 할 일
            } else {
                //dbList로 가져온 데이터를 가공하는 곳
            }
            withContext(Dispatchers.Main) {
//                dbAdapter.notifyDataSetChanged()
            }
            Log.d("dblist", "$dbList")
        }

    }

    private fun filter_recipe() {
        var MainIngerdientRank: Int = 0
        val indexes = mutableListOf<compare>()
        for (n in dbList.indices) {
            indexes.add(compare(dbList[n].name))
        }
        Log.d("lists", "$indexes")
        for (i in items.indices) {
            val name = items[i].name
            val IngerdientRank =
                (items[i].Ingredient).count() - (items[i].Ingredient).minus(
                    indexes.toList()
                ).count()
            for (k in indexes.indices) {
                if (indexes[k].Ingerdients == items[i].mainIngredient) {
                    MainIngerdientRank = k
                } else {
                    MainIngerdientRank = dbList.indices.count() + 1
                }
            }
            AfterFilter.add(SortMyRecipe(name, IngerdientRank, MainIngerdientRank))
        }
        Log.d("After", "$AfterFilter")

        AfterFilter.sortBy { it.IngredientRank }
        AfterFilter.reverse()
        AfterFilter.sortBy { it.MainRank }



        Log.d("match", "$items")
    }

    fun Imgtorecipe(i: Int) {
        howtorecipe[0] = items[i]?.step1.toString()
        howtorecipe[1] = items[i]?.step2.toString()
        howtorecipe[2] = items[i]?.step3.toString()
        howtorecipe[3] = items[i]?.step4.toString()
        howtorecipe[4] = items[i]?.step5.toString()
        howtorecipe[5] = items[i]?.step6.toString()
        howtorecipe[6] = items[i]?.step7.toString()
        howtorecipe[7] = items[i]?.step8.toString()
        howtorecipe[8] = items[i]?.step9.toString()
        howtorecipe[9] = items[i]?.step10.toString()
        howtorecipe[10] = items[i]?.step11.toString()
        howtorecipe[11] = items[i]?.step12.toString()
        howtorecipe[12] = items[i]?.step13.toString()
        howtorecipe[13] = items[i]?.step14.toString()
        howtorecipe[14] = items[i]?.step15.toString()
        howtorecipe[15] = items[i]?.step16.toString()
        howtorecipe[16] = items[i]?.step17.toString()
        howtorecipe[17] = items[i]?.step18.toString()
        howtorecipe[18] = items[i]?.step19.toString()
        howtorecipe[19] = items[i]?.step20.toString()
        howtorecipe[20] = items[i]?.step21.toString()
        howtorecipe[21] = items[i]?.step22.toString()
        howtorecipe[22] = null
    }

    fun insertRecipe(jsonArray: JSONArray) {
        for (i in 0 until jsonArray.length()) {
            val name = jsonArray.getJSONObject(i).getString("name")
            val chief = jsonArray.getJSONObject(i).getString("chief")
            val step1 = jsonArray.getJSONObject(i).getString("step1")
            val step2 = jsonArray.getJSONObject(i).getString("step2")
            val step3 = jsonArray.getJSONObject(i).getString("step3")
            val step4 = jsonArray.getJSONObject(i).getString("step4")
            val step5 = jsonArray.getJSONObject(i).getString("step5")
            val step6 = jsonArray.getJSONObject(i).getString("step6")
            val step7 = jsonArray.getJSONObject(i).getString("step7")
            val step8 = jsonArray.getJSONObject(i).getString("step8")
            val step9 = jsonArray.getJSONObject(i).getString("step9")
            val step10 = jsonArray.getJSONObject(i).getString("step10")
            val step11 = jsonArray.getJSONObject(i).getString("step11")
            val step12 = jsonArray.getJSONObject(i).getString("step12")
            val step13 = jsonArray.getJSONObject(i).getString("step13")
            val step14 = jsonArray.getJSONObject(i).getString("step14")
            val step15 = jsonArray.getJSONObject(i).getString("step15")
            val step16 = jsonArray.getJSONObject(i).getString("step16")
            val step17 = jsonArray.getJSONObject(i).getString("step17")
            val step18 = jsonArray.getJSONObject(i).getString("step18")
            val step19 = jsonArray.getJSONObject(i).getString("step19")
            val step20 = jsonArray.getJSONObject(i).getString("step20")
            val step21 = jsonArray.getJSONObject(i).getString("step21")
            val step22 = jsonArray.getJSONObject(i).getString("step22")
            val Ingredient = jsonArray.getJSONObject(i).getString("ingredient")
            val mainIngredient = jsonArray.getJSONObject(i).getString("mainIngredient")
            val url = jsonArray.getJSONObject(i).getString("link")
            val list = Ingredient.substring(1, Ingredient.length - 1).split(", ").toList()
            items.add(
                recipe(
                    name,
                    list,
                    mainIngredient,
                    chief,
                    step1,
                    step2,
                    step3,
                    step4,
                    step5,
                    step6,
                    step7,
                    step8,
                    step9,
                    step10,
                    step11,
                    step12,
                    step13,
                    step14,
                    step15,
                    step16,
                    step17,
                    step18,
                    step19,
                    step20,
                    step21,
                    step22,
                    url,
                )
            )
        }
    }


    override fun onDestroy() {
        // 해당 엑티비티 종료시
        exoPlayer?.pause()
        super.onDestroy()
    }

    override fun onStop() {
        // 백그라운드로 갔을때
        exoPlayer?.pause()
        super.onStop()
    }


    @SuppressLint("StaticFieldLeak")
    private fun playYoutubeLink(link: String) {
        object : YouTubeExtractor(this) {
            override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta?) {
                if (ytFiles != null) {
                    val itag = 22
                    val videoPath = ytFiles[itag].url
                    Log.d("YouTube Link", videoPath)
                    exoPlayer = ExoPlayer.Builder(this@RecipeRec).build()
                    buildMediaSource(videoPath)?.let {
                        exoPlayer?.addMediaSource(it)
                    }?.let {
                        videoPlayer?.player = exoPlayer
                    }
                }
            }
        }.extract(link)
    }

    private fun buildMediaSource(link: String): MediaSource? {
        Log.d("buildMediaSource", link)
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(link))
    }


    //test중이라 주석처리해둠
//    fun onDialogClicked2(view: View){
//        val check = Check(this)
//        check.show()
//    }
}