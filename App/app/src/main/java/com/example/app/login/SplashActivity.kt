package com.example.app.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.MainActivity
import com.example.app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        auth = Firebase.auth

        try {
            Log.d("Splash", auth.currentUser!!.uid)
            Toast.makeText(
                baseContext, "로그인 성공",
                Toast.LENGTH_SHORT
            ).show()
            Handler(Looper.myLooper()!!).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 1500)
        } catch (e: Exception) {
            Log.d("Splash", "need to sign in")
            Toast.makeText(
                baseContext, "로그인(회원가입)이 필요합니다",
                Toast.LENGTH_SHORT
            ).show()
            Handler().postDelayed({
                startActivity(Intent(this, Login::class.java))
                finish()
            }, 1500)
        }
    }
}