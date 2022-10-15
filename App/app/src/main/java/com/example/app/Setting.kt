package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.app.alert.Alert
import com.example.app.login.Login
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Setting : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val logout = findViewById<Button>(R.id.logout)

        logout.setOnClickListener {
            //로그아웃하고
            Firebase.auth.signOut()
            startActivity(Intent(this, Login::class.java))
            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()
            finish()
        }

        val noticepoint= findViewById<ImageButton>(R.id.noticepoint)
        noticepoint.setOnClickListener {
            val intent = Intent(this, NoticeMatter::class.java)
            startActivity(intent)
        }

        val alterPoint= findViewById<ImageButton>(R.id.alterPoint)
        alterPoint.setOnClickListener {
            val intent = Intent(this, Alert::class.java)
            startActivity(intent)
        }
    }

    fun onDialogClicked3(view: View){
        val name = Name(this)
        name.show()
    }
}