package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.APIS.recipe
import com.example.app.APIS.recipeapi
import com.example.app.APIS.recycler
import com.example.app.algorithm.enqueue
import com.example.app.algorithm.sort
import com.example.app.refrigerator.RefrigeratorStatus
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.json.JSONArray
import org.json.JSONObject
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
    var items = mutableListOf<recipe>()
    var howtorecipe = arrayOfNulls<String>(500)
    private var Booleanname: String? = null
//    private var youTubePlayerView = findViewById(R.id.video_player)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_rec)
        var require_in = findViewById(R.id.require_in) as TextView
        var recipe_name = findViewById(R.id.name_cook) as TextView
        var recipehow = findViewById<TextView>(R.id.pageview)
        var recipeto = findViewById<TextView>(R.id.recipe_howto) //레시피 텍스트박스
        var recipeHowCount = 1 //

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
            for (i in items.indices) {
                if (items[i].name == Booleanname) {
                    require_in.setText(items[i].Ingredient.toString())
                    recipe_name.setText(items[i].name)
                    Log.d("match", "$items")

                    howtorecipe[0] = items[i]?.step1.toString()
                    howtorecipe[1] = items[i]?.step2.toString()
                    howtorecipe[2] = items[i]?.step3.toString()
                    howtorecipe[3] = items[i]?.step4.toString()
                    howtorecipe[4] = items[i]?.step5.toString()
                    howtorecipe[5] = items[i]?.step6.toString()
                    howtorecipe[6] = items[i]?.step7.toString()
                    howtorecipe[7] = items[i]?.step8.toString()
                    recipeto.setText(howtorecipe[i])
                    recipehow.setText(recipeHowCount.toString())
                }
            }
        } else {
            Thread {
                val test = enqueue().getRecipeArray();
                if (test != null) {
                    items = sort().orderByIngredient(
                        test, listOf<String>(
                            "onion",
                            "chilipepper",
                            "kimchi",
                            "tofu"
                        )
                    );

                }

                Log.d("test", "$test")
                for (i in items.indices) {
                    val matchCounter =
                        (items[i].Ingredient).count() - (items[i].Ingredient).minus(
                            listOf<String>(
                                "onion",
                                "chilipepper",
                                "kimchi",
                                "tofu",
                                "soyhbeanpaste",
                                "shiitake"
                            )
                        ).count()
                    items[i].matchCount = matchCounter
                    Log.d("count", "$matchCounter")

                }
                items.sortBy { it.matchCount }
                items.reverse()
//            getListData()
                Log.d("recipes", "$items")

            }.start()

//            require_in.setText(items[0].Ingredient.toString())
//            recipe_name.setText(items[0].name)
//            Log.d("match", "$items")
//
//            howtorecipe[0] = items[0]?.step1.toString()
//            howtorecipe[1] = items[0]?.step2.toString()
//            howtorecipe[2] = items[0]?.step3.toString()
//            howtorecipe[3] = items[0]?.step4.toString()
//            howtorecipe[4] = items[0]?.step5.toString()
//            howtorecipe[5] = items[0]?.step6.toString()
//            howtorecipe[6] = items[0]?.step7.toString()
//            howtorecipe[7] = items[0]?.step8.toString()
////                setHowtoRecipe()
//            recipeto.setText(howtorecipe[0])
//            recipehow.setText(recipeHowCount.toString())
        }


//        var gson = GsonBuilder().setLenient().create()
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://jaeryurp.duckdns.org:40131/")
////            .addConverterFactory(ScalarsConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create(gson)) //있으나마나한 코드...
//            .build()
//        val api = retrofit.create(recipeapi::class.java)
//
//        val callResult = api.getResult()
//        var resultJsonArray: JsonArray?

//
//        callResult.enqueue(object : Callback<JsonArray> {
//            override fun onResponse(
//                call: Call<JsonArray>,
//                response: Response<JsonArray>
//            ) {
////                Log.d("FeatTwo", "성공 : ${response.body()}")
//                resultJsonArray = response.body()
////                Log.d("FeatTwo", "json : ${resultJsonArray.toString()}")
//
//                val jsonArray = JSONTokener(resultJsonArray.toString()).nextValue() as JSONArray
//                val convertArray =
//                    MutableList<JSONObject>(jsonArray.length()) { i -> jsonArray.getJSONObject(i) }.map {
//                        Gson().fromJson<HashMap<String, String>>(
//                            it.toString(),
//                            HashMap::class.java
//                        )
//                    }
//                val filterMainIngredient =
//                    convertArray.filter { it?.get("mainIngredient") == "tomato" }
////                val mainItems = filterMainIngredient.filter { it?.get("chief") == "A" }
////                val subItems = filterMainIngredient.filter { it?.get("chief") != "A" }
//                for (i in 0 until jsonArray.length()) {
//                    val name = jsonArray.getJSONObject(i).getString("name")
//                    val chief = jsonArray.getJSONObject(i).getString("chief")
//                    val step1 = jsonArray.getJSONObject(i).getString("step1")
//                    val step2 = jsonArray.getJSONObject(i).getString("step2")
//                    val step3 = jsonArray.getJSONObject(i).getString("step3")
//                    val step4 = jsonArray.getJSONObject(i).getString("step4")
//                    val step5 = jsonArray.getJSONObject(i).getString("step5")
//                    val step6 = jsonArray.getJSONObject(i).getString("step6")
//                    val step7 = jsonArray.getJSONObject(i).getString("step7")
//                    val step8 = jsonArray.getJSONObject(i).getString("step8")
//                    val Ingredient = jsonArray.getJSONObject(i).getString("ingredient")
//                    val mainIngredient = jsonArray.getJSONObject(i).getString("mainIngredient")
//                    val url = jsonArray.getJSONObject(i).getString("link")
//                    val match = 0
//                    val list = Ingredient.substring(1, Ingredient.length - 1).split(", ").toList()
//                    items.add(
//                        recipe(
//                            name,
//                            list,
//                            mainIngredient,
//                            chief,
//                            step1,
//                            step2,
//                            step3,
//                            step4,
//                            step5,
//                            step6,
//                            step7,
//                            step8,
//                            url,
//                            match
//                        )
//                    )
//                }
//                val mainItems = items.filter { it.chief == "A" }
//                val subItems = items.filter { it.chief != "A" }
//        Log.d("List", "$items")
//        if (Booleanname != null) {
//            for (i in items.indices) {
//                if (items[i].name == Booleanname) {
//                    require_in.setText(items[i].Ingredient.toString())
//                    recipe_name.setText(items[i].name)
//                    Log.d("match", "$items")
//
//                    howtorecipe[0] = items[i]?.step1.toString()
//                    howtorecipe[1] = items[i]?.step2.toString()
//                    howtorecipe[2] = items[i]?.step3.toString()
//                    howtorecipe[3] = items[i]?.step4.toString()
//                    howtorecipe[4] = items[i]?.step5.toString()
//                    howtorecipe[5] = items[i]?.step6.toString()
//                    howtorecipe[6] = items[i]?.step7.toString()
//                    howtorecipe[7] = items[i]?.step8.toString()
//                    recipeto.setText(howtorecipe[i])
//                    recipehow.setText(recipeHowCount.toString())
//                }
//            }
//        } else {
//            for (i in items.indices) {
//                val matchCounter =
//                    (items[i].Ingredient).count() - (items[i].Ingredient).minus(
//                        listOf<String>(
//                            "onion",
//                            "chilipepper",
//                            "kimchi",
//                            "tofu",
//                            "soyhbeanpaste",
//                            "shiitake"
//                        )
//                    ).count()
//                items[i].matchCount = matchCounter
//                Log.d("count", "$matchCounter")
//
//            }
//            items.sortBy { it.matchCount }
//            items.reverse()
//            getListData()
//            require_in.setText(items[0].Ingredient.toString())
//            recipe_name.setText(items[0].name)
//            Log.d("match", "$items")
//
//            howtorecipe[0] = items[0]?.step1.toString()
//            howtorecipe[1] = items[0]?.step2.toString()
//            howtorecipe[2] = items[0]?.step3.toString()
//            howtorecipe[3] = items[0]?.step4.toString()
//            howtorecipe[4] = items[0]?.step5.toString()
//            howtorecipe[5] = items[0]?.step6.toString()
//            howtorecipe[6] = items[0]?.step7.toString()
//            howtorecipe[7] = items[0]?.step8.toString()
//            recipeto.setText(howtorecipe[0])
//            recipehow.setText(recipeHowCount.toString())
//        }


        val left = findViewById<Button>(R.id.left_btn)
        val right = findViewById<Button>(R.id.right_btn)
        left.setOnClickListener {
            if (recipeHowCount > 1) {
                recipeHowCount--
                recipehow.setText((recipeHowCount).toString())
                recipeto.setText(howtorecipe[recipeHowCount - 1])
            }
        }
        right.setOnClickListener {
            recipeHowCount++
            if (howtorecipe[recipeHowCount - 1]?.isEmpty() == true) recipeHowCount--
            else {
                recipehow.setText((recipeHowCount).toString())
                recipeto.setText(howtorecipe[recipeHowCount - 1])
            }
        }
    }

    fun onDialogClicked2(view: View) {
        val check = Check(this)
        check.show()
    }

}