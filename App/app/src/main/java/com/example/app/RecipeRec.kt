package com.example.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.app.APIS.recipe
import com.example.app.APIS.recipeapi
import com.example.app.APIS.recycler
import com.example.app.RecAdapter.EndCook
import com.example.app.localdb.RoomExpDB
import com.example.app.localdb.RoomHelper
import com.example.app.refrigerator.RefrigeratorStatus
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
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

class RecipeRec : AppCompatActivity() {
    private val serviceKey = "Cname"
    private var container = -1
    private lateinit var recipeRecyclerview: RecyclerView
    private lateinit var recyclerArray: ArrayList<recipe>
    var addlist = mutableListOf<recycler>()
    val items = mutableListOf<recipe>()
    var howtorecipe = arrayOfNulls<String>(500)
    private var Booleanname: String? = null
    private var KeyAPI: String? = null
    val dbList = mutableListOf<RoomExpDB>()
    lateinit var helper: RoomHelper
    var Extra = mutableListOf<EndCook>()
    var Sort = 0

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_rec)
        var require_in = findViewById(R.id.require_in) as TextView
        var recipe_name = findViewById(R.id.name_cook) as TextView
        var recipehow = findViewById<TextView>(R.id.pageview)
        videoPlayer = findViewById(R.id.video_player)

        helper = Room.databaseBuilder(baseContext, RoomHelper::class.java, "internalExpDb")
            .build()
        getRoomDb()

        val home = findViewById<ImageButton>(R.id.home)
        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val refrigerator = findViewById<ImageButton>(R.id.refrigerator)
        refrigerator.setOnClickListener {
            val intent = Intent(this, RefrigeratorStatus::class.java)
            startActivity(intent)
        }

        if (intent.hasExtra("BooleanName")) {
            Booleanname = intent.getStringExtra("BooleanName")
            Log.d("Boolean", "$Booleanname")
        }
        if (intent.hasExtra("key"))
            KeyAPI = intent.getStringExtra("key")
        var api = retrofit.create(recipeapi::class.java)

        val callResult: Call<JsonArray>?
        if (KeyAPI == "korean") {
            callResult = api.getResult()
        } else if (KeyAPI == "japan") {
            callResult = api.getResult()
        } else if (KeyAPI == "american") {
            callResult = api.getResult()
        } else if (KeyAPI == "china") {
            callResult = api.getChina()
        } else if (KeyAPI == "bunsik") {
            callResult = api.getResult()
        } else if (KeyAPI == "meat") {
            callResult = api.getResult()
        } else if (KeyAPI == "seafood") {
            callResult = api.getResult()
        } else if (KeyAPI == "random") {
            callResult = api.getResult()
        } else if (KeyAPI == "image") {
            callResult = api.getResult()
        } else {
            callResult = api.getResult()
        }

        var resultJsonArray: JsonArray?
        var recipeto = findViewById<TextView>(R.id.recipe_howto)
        var recipeHowCount = 1

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
//                            exoPlayer = SimpleExoPlayer.Builder(this).build()
//                            videoPlayer?.player = exoPlayer
//                            buildMediaSource(items[i].link)?.let{
//                                videoPlayer?.prepare(it)
//                            }
                        }
                    }
                } else {
                    require_in.setText(items[0].Ingredient.toString())
                    recipe_name.setText(items[0].name)
                    Log.d("match", "$items")

                    Imgtorecipe(0)
                    recipeto.setText(howtorecipe[0])
                    recipehow.setText(recipeHowCount.toString())

//                    exoPlayer = SimpleExoPlayer.Builder(this).build()
//                    videoPlayer?.player = exoPlayer
//                    buildMediaSource(items[0].link)?.let{
//                        videoPlayer?.prepare(it)
//                    }
                }
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
            }else Toast.makeText(
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

    }

    fun onDialogClicked2(view: View) {
        for(i in items[Sort].Ingredient.indices){
            val split = items[Sort].Ingredient[i].split(' ')
            val ingredient = split[0]
            val used = split[1]

            Extra.add(EndCook(ingredient,used))

        }
        Log.d("Extra", "$Extra")
        val check = Check(Extra,this)
        check.show()
    }

    private fun useAPI(key: String?) {

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
        val i = 0
        val n = 0
        val indexes = mutableListOf<String>()
        for (n in dbList.indices) {
            indexes.add(dbList[n].name)
        }
        Log.d("lists", "$indexes")
        for (i in items.indices) {
            val matchCounter =
                (items[i].Ingredient).count() - (items[i].Ingredient).minus(
                    indexes.toList()
                ).count()
            items[i].matchCount = matchCounter
            Log.d("count", "$matchCounter")
        }


        items.sortBy { it.matchCount }
        items.reverse()
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
            val match = 0
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
                    match
                )
            )
        }
    }

//    private fun buildMediaSource(link):MediaSource?{
//        val dataSourceFactory = DefaultDataSource(this,"sample")
//        return ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(link))
//    }

}