package com.example.app

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.app.login.SettingUserName
import kotlinx.android.synthetic.main.activity_name.*
import kotlin.random.Random

class Name(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        sample_EditText.setText(getName("name"))

        store.setOnClickListener {
            setName(sample_EditText.text.toString())
            dismiss()
        }
    }
    private fun setName(values: String) {
        val prefs = context.getSharedPreferences(SettingUserName.sharedPrefFileName, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("name", values)
        editor.apply()
    }

    fun getName(key: String): String? {
        val prefs = context.getSharedPreferences(SettingUserName.sharedPrefFileName, Context.MODE_PRIVATE)
        val value = prefs.getString(key, "User${Random(1000).nextInt(10000)}") ?: "User+${Random(10000).nextInt()}"
        Log.d("name", "$value")
        return value
    }
}