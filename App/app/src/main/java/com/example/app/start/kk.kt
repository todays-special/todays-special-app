package com.example.app

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class kk : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kk)

        var dataArr = arrayOf("냄비", "후라이팬", "전자레인지", "에어후라이")
        val btn1 = findViewById<Button>(R.id.btn1)

        btn1.setOnClickListener{

            var a = ArrayList<Int>()

            var builder = AlertDialog.Builder(this)
            builder.setTitle("선택")
            builder.setMultiChoiceItems(
                dataArr,
                null,
                object : DialogInterface.OnMultiChoiceClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int, isChecked: Boolean) {
                        if(isChecked){
                            a.add(which)
                        } else if(a.contains(which)){
                            a.remove(which)
                        }
                    }
                }
            )
        }
    }

    private fun getTools(key: String): ArrayList<String>{
        val prefs = getSharedPreferences("tools", Context.MODE_PRIVATE)
        val json = prefs.getString(key, "[]")
        val gson = Gson()
        //저장되어있는 키워드를 받아서 배열의 형태로 리턴시켜주게 됨
        return gson.fromJson(
            json,
            object : TypeToken<ArrayList<String?>>() {}.type
        )
    }

    private fun saveTools(key:String , values: ArrayList<String>){
        val gson = Gson()
        val json = gson.toJson(values)
        val prefs = getSharedPreferences("tools", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(key, json)
        editor.apply()
    }
}