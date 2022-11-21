package com.example.app

import android.app.Application
import com.example.app.RecAdapter.SharedPer

class MyApplication : Application() {
    companion object {
        lateinit var Used:SharedPer
    }

    override fun onCreate() {
        Used = SharedPer(applicationContext)
        super.onCreate()
    }
}