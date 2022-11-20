package com.example.app

import android.app.Application
import com.example.app.RecAdapter.SharedPer
import com.example.app.RecAdapter.SharedUsed

class MyApplication : Application() {
    companion object {
        lateinit var Ingerd: SharedPer
        lateinit var Used:SharedUsed
    }

    override fun onCreate() {
        Ingerd = SharedPer(applicationContext)
        Used = SharedUsed(applicationContext)
        super.onCreate()
    }
}