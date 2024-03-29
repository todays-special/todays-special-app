package com.example.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.app.APIS.SortMyRecipe
import com.example.app.APIS.compare
import com.example.app.APIS.recipe
import com.example.app.APIS.recipeapi
import com.example.app.MainTop.MainTop
import com.example.app.MainTop.MainTopAdapter
import com.example.app.alert.AlertSetting
import com.example.app.localdb.RoomExpDB
import com.example.app.localdb.RoomHelper
import com.example.app.login.New_customer
import com.example.app.login.SettingUserName
import com.example.app.refrigerator.Exp
import com.example.app.refrigerator.ExpExpiredAdapter
import com.example.app.refrigerator.ExpFineAdapter
import com.example.app.refrigerator.RefrigeratorStatus
import com.example.app.shopping.Shopping
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random


const val testUrl =
    "https://img.danawa.com/prod_img/500000/616/833/img/3833616_1.jpg?shrink=330:330&_v=20170329122809"

class MainActivity : AppCompatActivity() {

    //    private fun getName(key: String): String? {
//        val prefs = getSharedPreferences(New_customer.sharedPrefFileName, Context.MODE_PRIVATE)
//        val value = prefs.getString(key, " ") ?: "User+${Random(10000).nextInt()}"
//        Log.d("name", "$value")
//        return value
//    }

    lateinit var itemName: String
    var AfterFilter = mutableListOf<SortMyRecipe>()
    var items = mutableListOf<recipe>()
    var choice = ThreadLocalRandom.current().nextInt(1,10)
    lateinit var rv: RecyclerView
    val dbList = mutableListOf<RoomExpDB>()
    lateinit var helper: RoomHelper
    val mainIngList = mutableListOf<MainTop>()
    lateinit var mainAdapter: MainTopAdapter
    var result = ""
    fun getRoomDb() {
        helper = Room.databaseBuilder(baseContext, RoomHelper::class.java, "internalExpDb")
            .build()
        if (dbList.isNotEmpty()) {
            dbList.clear()
        }
        CoroutineScope(Dispatchers.IO).launch {
            //room에 있는 데이터 가져오기
            dbList.addAll(helper.roomExpDao().getAll())
            //RoomDb가 존재하지 않으면 build하도록
            if (dbList.size == 0) {

            } else {
                //dbList로 가져온 데이터를 가공하는 곳
                val today = Calendar.getInstance()
                val sf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

                for (i in dbList) {
                    //5일 단위.
                    val date = sf.parse(i.exp)
                    val calcDate = (date.time - today.time.time) / (60 * 60 * 24 * 1000)
                    if (calcDate in 0..5) {
                        mainIngList.add(MainTop(testUrl, i.name))
                        val processedName = IngredientData().getNameFromId(i.name)
                        result += "${processedName}, "
                    }
//                    Log.d("Main", "$mainIngList")

                }
            }
            delay(1000)
            Log.d("mainnoti","$result")
            sendNotification("식재료알림", "${result}가 임박했습니다")
            withContext(Dispatchers.Main) {
//                dbAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onStart() {
        super.onStart()
//        getRoomDb()
    }

    private fun setCheck() {
        val prefs = getSharedPreferences("main", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("main_check", "checked")
        editor.apply()
    }

    private fun getCheck(): String? {
        val prefs = getSharedPreferences("main", Context.MODE_PRIVATE)
        val value = prefs.getString("main_check", "default")
        Log.d("main_check", "$value")
        return value
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_App)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val imageBtn = findViewById<ImageView>(R.id.recipeImageBtn)

        val rv = findViewById<RecyclerView>(R.id.main_rv)

        val guideState = getCheck()
        if (guideState == "checked") {
            guide.visibility = View.GONE
        } else {
            delete.setOnClickListener {
                guide.visibility = View.GONE
                if (check.isChecked) {
                    Log.d("MainCheck", "checked")
                    //다시 안뜨게
                    setCheck()
                    Toast.makeText(baseContext, "가이드가 다시 나오지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }


        var gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://jaeryurp.duckdns.org:40131/")
//            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson)) //있으나마나한 코드...
            .build()
        val api = retrofit.create(recipeapi::class.java)

        val callResult = api.getChina()
        var resultJsonArray: JsonArray?

//        helper = Room.databaseBuilder(baseContext, RoomHelper::class.java, "internalExpDb")
//            .build()
        getRoomDb()

        shopping.setOnClickListener {
            val intent = Intent(this, Shopping::class.java)
            intent.putExtra("igred",result)
            startActivity(intent)
        }

        callResult.enqueue(object : Callback<JsonArray> {
            override fun onResponse(
                call: Call<JsonArray>,
                response: Response<JsonArray>
            ) {
                resultJsonArray = response.body()

                val jsonArray = JSONTokener(resultJsonArray.toString()).nextValue() as JSONArray
                insertRecipe(jsonArray)

                filter_recipe()
                itemName = items[choice].name
                glideimg(items[choice].link, imageBtn)

            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("FeatTwo", "실패 : $t")
            }
        })

        //상단 재료 바
        mainAdapter = MainTopAdapter(mainIngList)
        rv.adapter = mainAdapter
        rv.layoutManager = LinearLayoutManager(baseContext).apply {
            orientation = RecyclerView.HORIZONTAL
            mainAdapter.notifyDataSetChanged()
        }

        mainAdapter.itemClick = object : MainTopAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                //여기서 재료랑 밑에 사진 맵핑
                val maincheck = mainIngList[position].name
                for(i in items.indices){
                    if(items[i].mainIngredient == maincheck){
                        glideimg(items[i].link,imageBtn)
                        itemName = items[i].name
                    }
                }
            }
        }

//        // 리사이클러뷰 아이템을 클릭하면 여기가 호출됨
//        mainAdapter.itemClick = object : MainTopAdapter.ItemClick {
//            override fun onClick(view: View, position: Int) {
//                //여기서 재료랑 밑에 사진 맵핑
//                val itemName = mainIngList[position].name
//                Toast.makeText(baseContext, "$itemName", Toast.LENGTH_SHORT).show()
//                Log.d("MainClick", ""+IngredientImgUrl().dataList[mainIngList[position].name])
//            }
//        }

//        val imageView4 = findViewById<ImageView>(R.id.imageView4)
//        val imageView5 = findViewById<ImageView>(R.id.imageView5)
//        val imageView3 = findViewById<ImageView>(R.id.imageView3)

        val bell = findViewById<ImageButton>(R.id.bell)
        bell.setOnClickListener {
            val intent = Intent(this, Notice::class.java)
            startActivity(intent)
        }

        main_name.text = "${SettingUserName().getName("name", this).toString()}의 냉장고"


        //하단바 이동
        val refrigerator = findViewById<ImageButton>(R.id.refrigerator)
        refrigerator.setOnClickListener {
            val intent = Intent(this, RefrigeratorStatus::class.java)
            startActivity(intent)
        }

        val suljung = findViewById<ImageButton>(R.id.suljung)
        suljung.setOnClickListener {
            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }


        //레시피 이동 , 한식
        val korean = findViewById<ImageButton>(R.id.korean)
        korean.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("key", "korea")
            startActivity(intent)
        }
        //레시피 이동 , 일식
        val japan = findViewById<ImageButton>(R.id.japan)
        japan.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("key", "japan")
            startActivity(intent)
        }
        //레시피 이동 , 양식
        val american = findViewById<ImageButton>(R.id.american)
        american.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("key", "western")
            startActivity(intent)
        }
        //레시피 이동 , 중식
        val china = findViewById<ImageButton>(R.id.china)
        china.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("key", "china")
            startActivity(intent)
        }
        //레시피 이동 , 분식
        val bunsik = findViewById<ImageButton>(R.id.bunsik)
        bunsik.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("key", "bunsik")
            startActivity(intent)
        }
        //레시피 이동 , 육류
        val meat = findViewById<ImageButton>(R.id.meat)
        meat.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("key", "meat")
            startActivity(intent)
        }
        //레시피 이동, 해산물
        val seafood = findViewById<ImageButton>(R.id.seafood)
        seafood.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("key", "seafood")
            startActivity(intent)
        }
        //레시피 이동, 랜덤
        val random = findViewById<ImageButton>(R.id.random)
        random.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("key", "random")
            startActivity(intent)
        }

        imageBtn.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("BooleanName", itemName)
            intent.putExtra("key", "image")
            startActivity(intent)
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

    fun glideimg(link: String, imageBtn: ImageView) {
        var glidelink = link.substring(link.lastIndexOf("=") + 1)
        glidelink = glidelink.substring(0, 11)
        val getThumbnail = "https://i1.ytimg.com/vi/" + glidelink + "/" + "maxresdefault.jpg"
        Glide.with(imageBtn)
            .load(getThumbnail)
            .centerCrop()
            .fitCenter()
            .into(imageBtn)
    }

    override fun onResume() {
        super.onResume()
//        getRoomDb()
        mainAdapter.notifyDataSetChanged()
        main_name.text = "${SettingUserName().getName("name", this).toString()}의 냉장고"
    }


    //notify
    fun sendNotification(title: String = "", message: String = "", channelId: String = "") {

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
//        val notificationID = Random().nextInt(1000) // 0~999의 정수 랜덤 추출

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager, AlertSetting.CHANNEL_ID)
        }
        //야간설정
        if (title.isNotEmpty() && message.isNotEmpty()) {
            val notification = NotificationCompat.Builder(this, AlertSetting.CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
                .build()
            notificationManager.notify(AlertSetting.notificationID_ALL, notification)
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
}