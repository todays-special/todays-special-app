package com.example.app.RecAdapter

import android.content.Context
import android.content.SharedPreferences

class SharedPer(context:Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("Ends_ingred", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()


    }
}
