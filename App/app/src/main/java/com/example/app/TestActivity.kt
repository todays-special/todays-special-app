package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    override fun onResume() {
        super.onResume()
    }
}