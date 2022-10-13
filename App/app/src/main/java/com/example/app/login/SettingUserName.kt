package com.example.app.login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import kotlin.random.Random

class SettingUserName {

    private lateinit var mPreferences: SharedPreferences

    companion object {
        const val sharedPrefFileName = "name"
    }

    //사용자 이름 설정하기 -> sharedPreference로 저장하기 때문에 로컬파일로 남게됨.
    private fun setName(values: String, context: Context) {
        val prefs = context.getSharedPreferences(sharedPrefFileName, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("name", values)
        editor.apply()
    }

    //사용자 이름 가져오기
     fun getName(key: String, context: Context): String? {
        val prefs = context.getSharedPreferences(sharedPrefFileName, Context.MODE_PRIVATE)
        val value = prefs.getString(key, "User${Random(1000).nextInt(10000)}") ?: "User+${Random(10000).nextInt()}"
        Log.d("name", "$value")
        return value
    }
}