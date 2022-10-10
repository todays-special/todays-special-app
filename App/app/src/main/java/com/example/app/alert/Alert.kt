package com.example.app.alert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.widget.SwitchCompat
import com.example.app.R
import com.example.app.Setting

class Alert : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alter)

        val back= findViewById<ImageButton>(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }

        val switch1 = findViewById<SwitchCompat>(R.id.switch1)
        val switch2 = findViewById<SwitchCompat>(R.id.switch2)
        val switch3 = findViewById<SwitchCompat>(R.id.switch3)
        val switch4 = findViewById<SwitchCompat>(R.id.switch4)

        switch1.isChecked = true
        switch2.isChecked = true
        switch3.isChecked = true
        switch4.isChecked = true


        switch1.setOnClickListener{
            if (switch1.isChecked){
                switch2.isChecked = true
                switch3.isChecked = true
                switch4.isChecked = true
            }
            else {
                switch2.isChecked = false
                switch3.isChecked = false
                switch4.isChecked = false
            }
        }

        switch2.setOnClickListener{
            if(switch2.isChecked){

            }
            else{
                switch1.isChecked = false
            }
        }
        switch3.setOnClickListener{
            if(switch3.isChecked){

            }
            else{
                switch1.isChecked = false
            }
        }
        switch4.setOnClickListener{
            if(switch4.isChecked){

            }
            else{
                switch1.isChecked = false
            }
        }


    }
}