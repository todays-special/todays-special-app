package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
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
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ThreadLocalRandom

class MainActivity : AppCompatActivity() {
    private val serviceKey = "Cname"
    private var container = -1
    var addlist = mutableListOf<recycler>()
    var items = mutableListOf<recipe>()
    var choice = 2 //ThreadLocalRandom.current().nextInt(1,2)


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_App)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageBtn = findViewById<ImageButton>(R.id.recipeImageBtn)

        var gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://jaeryurp.duckdns.org:40131/")
//            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson)) //있으나마나한 코드...
            .build()
        val api = retrofit.create(recipeapi::class.java)

        val callResult = api.getResult()
        var resultJsonArray: JsonArray?

        callResult.enqueue(object : Callback<JsonArray> {
            override fun onResponse(
                call: Call<JsonArray>,
                response: Response<JsonArray>
            ) {
                resultJsonArray = response.body()

                val jsonArray = JSONTokener(resultJsonArray.toString()).nextValue() as JSONArray

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
                            url,
                            match
                        )
                    )
                }
                Log.d("List", "$items")

                filter_recipe()

            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("FeatTwo", "실패 : $t")
            }
        })



        if (choice == 1) {
            imageBtn.setImageResource(R.drawable.kimchijji)
        } else {
            imageBtn.setImageResource(R.drawable.d)
        }

        val bell = findViewById<ImageButton>(R.id.bell)
        bell.setOnClickListener {
            val intent = Intent(this, Notice::class.java)
            startActivity(intent)
        }

        //토큰
        val tokenbtn1 = findViewById<ImageButton>(R.id.token1)
        tokenbtn1.setOnClickListener {
            choice = 1
            imageBtn.setImageResource(R.drawable.kimchijji)
        }
        val tokenbtn2 = findViewById<ImageButton>(R.id.token2)
        tokenbtn2.setOnClickListener {
            choice = 2
            imageBtn.setImageResource(R.drawable.d)
        }


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
            intent.putExtra("key","korea")
            startActivity(intent)
        }
        //레시피 이동 , 일식
        val japan = findViewById<ImageButton>(R.id.japan)
        japan.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("key","japan")
            startActivity(intent)
        }
        //레시피 이동 , 양식
        val american = findViewById<ImageButton>(R.id.american)
        american.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("key","amreican")
            startActivity(intent)
        }
        //레시피 이동 , 중식
        val china = findViewById<ImageButton>(R.id.china)
        china.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("key","china")
            startActivity(intent)
        }
        //레시피 이동 , 분식
        val bunsik = findViewById<ImageButton>(R.id.bunsik)
        bunsik.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("key","bunsik")
            startActivity(intent)
        }
        //레시피 이동 , 육류
        val meat = findViewById<ImageButton>(R.id.meat)
        meat.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("key","meat")
            startActivity(intent)
        }
        //레시피 이동, 해산물
        val seafood = findViewById<ImageButton>(R.id.seafood)
        seafood.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("key","seafood")
            startActivity(intent)
        }
        //레시피 이동, 랜덤
        val random = findViewById<ImageButton>(R.id.random)
        random.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("key","random")
            startActivity(intent)
        }

        imageBtn.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("BooleanName", items[choice - 1].name)
            intent.putExtra("key","image")
            startActivity(intent)
        }

    }
    private fun filter_recipe(){
        val i = 0
        for(i in items.indices) {
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
        Log.d("match", "$items")
    }


}