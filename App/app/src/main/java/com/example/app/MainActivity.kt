package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
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

        Thread {
            val test = enqueue().getRecipeArray();
            if (test != null) {
                items = sort().orderByIngredient(test, listOf<String>(
                    "onion",
                    "chilipepper",
                    "kimchi",
                    "tofu"
                ));

            }

            Log.d("test","$test")
        }.start()



        if (choice == 1) {
            imageBtn.setImageResource(R.drawable.kimchijji)
        } else {
            imageBtn.setImageResource(R.drawable.d)
        }

        val bell= findViewById<ImageButton>(R.id.bell)
        bell.setOnClickListener {
            val intent = Intent(this, Notice::class.java)
            startActivity(intent)
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
            startActivity(intent)
        }
        //레시피 이동 , 일식
        val japan = findViewById<ImageButton>(R.id.japan)
        japan.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            startActivity(intent)
        }
        //레시피 이동 , 양식
        val american = findViewById<ImageButton>(R.id.american)
        american.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            startActivity(intent)
        }
        //레시피 이동 , 중식
        val china = findViewById<ImageButton>(R.id.china)
        china.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            startActivity(intent)
        }
        //레시피 이동 , 분식
        val bunsik = findViewById<ImageButton>(R.id.bunsik)
        bunsik.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            startActivity(intent)
        }
        //레시피 이동 , 육류
        val meat = findViewById<ImageButton>(R.id.meat)
        meat.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            startActivity(intent)
        }
        //레시피 이동, 해산물
        val seafood = findViewById<ImageButton>(R.id.seafood)
        seafood.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            startActivity(intent)
        }
        //레시피 이동, 랜덤
        val random = findViewById<ImageButton>(R.id.random)
        random.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            startActivity(intent)
        }

        imageBtn.setOnClickListener{
            val intent = Intent(this, RecipeRec::class.java)
            intent.putExtra("BooleanName",items[choice-1].name)
            startActivity(intent)
        }

    }



}