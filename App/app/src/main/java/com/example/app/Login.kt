package com.example.app

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login2)

        val add_customer = findViewById<ConstraintLayout>(R.id.add_customer)
        add_customer.setOnClickListener {
            val intent = Intent(this, New_customer::class.java)
            startActivity(intent)
        }
        val find = findViewById<TextView>(R.id.find)
        find.setOnClickListener {
            val intent = Intent(this, Find::class.java)
            startActivity(intent)
        }
    }
}