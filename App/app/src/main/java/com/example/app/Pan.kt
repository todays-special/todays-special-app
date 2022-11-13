package com.example.app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.Toast
import com.example.app.refrigerator.RefrigeratorStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_pan.*
import kotlinx.android.synthetic.main.activity_pan.view.*


class Pan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pan)
        val panView = findViewById<CheckBox>(R.id.pan)
        val pan: Array<String> = resources.getStringArray(R.array.pan)
        val toolsList = getTools("tool")
        Log.d("toolList", "$toolsList  , ${pan}")
            // 조리도구 체크하기
        for (i in pan) {
            if (toolsList.contains(i)) {
                //check 되게
                Log.d("checked", "$i")
                when (i) {
                    "후라이팬" -> {
                        panView.isChecked = true
                    }
                    "냄비" -> {
                        pot.isChecked = true
                    }
                }
            }
        }

        val store = findViewById<ImageButton>(R.id.store)
        val back = findViewById<ImageButton>(R.id.back)
        //조리도구 체크
        panView.setOnClickListener {
            //
        }
        back.setOnClickListener {
            finish()
        }

        store.setOnClickListener {
            Toast.makeText(this@Pan, "저장되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getTools(key: String): ArrayList<String> {
        val prefs = getSharedPreferences("tools", Context.MODE_PRIVATE)
        val json = prefs.getString(key, "[]")
        val gson = Gson()
        //저장되어있는 키워드를 받아서 배열의 형태로 리턴시켜주게 됨
        return gson.fromJson(
            json,
            object : TypeToken<ArrayList<String?>>() {}.type
        )
    }
}