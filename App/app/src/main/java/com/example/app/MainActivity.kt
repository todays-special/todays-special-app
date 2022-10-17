package com.example.app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import com.example.app.login.New_customer
import com.example.app.login.SettingUserName
import com.example.app.refrigerator.RefrigeratorStatus
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

//    private fun getName(key: String): String? {
//        val prefs = getSharedPreferences(New_customer.sharedPrefFileName, Context.MODE_PRIVATE)
//        val value = prefs.getString(key, " ") ?: "User+${Random(10000).nextInt()}"
//        Log.d("name", "$value")
//        return value
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_App)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView4 = findViewById<ImageView>(R.id.imageView4)
        val imageView5 = findViewById<ImageView>(R.id.imageView5)
        val imageView3 = findViewById<ImageView>(R.id.imageView3)

        val bell= findViewById<ImageButton>(R.id.bell)
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

        val name2 = findViewById<ImageButton>(R.id.name2)
        name2.setOnClickListener{
            imageView4.visibility=View.INVISIBLE
            imageView5.visibility=View.INVISIBLE
            imageView3.visibility=View.VISIBLE
        }

        val name1 = findViewById<ImageButton>(R.id.name1)
        name1.setOnClickListener{
            imageView4.visibility=View.VISIBLE
            imageView5.visibility=View.INVISIBLE
            imageView3.visibility=View.INVISIBLE

        }

        val name3 = findViewById<ImageButton>(R.id.name3)
        name3.setOnClickListener{
            imageView4.visibility=View.INVISIBLE
            imageView5.visibility=View.VISIBLE
            imageView3.visibility=View.INVISIBLE

        }
    }

    override fun onResume() {
        super.onResume()
        main_name.text = "${SettingUserName().getName("name", this).toString()}의 냉장고"
    }
}