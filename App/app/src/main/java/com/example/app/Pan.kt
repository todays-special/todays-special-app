package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import com.example.app.refrigerator.RefrigeratorStatus
import kotlinx.android.synthetic.main.activity_pan.*


class Pan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pan)

        val store = findViewById<ImageButton>(R.id.store)
        val back = findViewById<ImageButton>(R.id.back)
        //조리도구 체크
        pan.setOnClickListener {
            //
        }
        back.setOnClickListener {
            finish()
        }

        store.setOnClickListener {
            Toast.makeText(this@Pan, "저장되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}