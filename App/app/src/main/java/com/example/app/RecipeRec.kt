package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.example.app.refrigerator.RefrigeratorStatus

class RecipeRec : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_rec)

        val home= findViewById<ImageButton>(R.id.home)
        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val refrigerator = findViewById<ImageButton>(R.id.refrigerator)
        refrigerator.setOnClickListener {
            val intent = Intent(this, RefrigeratorStatus::class.java)
            startActivity(intent)
        }
    }
    fun onDialogClicked2(view: View){
        val check = Check(this)
        check.show()
    }
}