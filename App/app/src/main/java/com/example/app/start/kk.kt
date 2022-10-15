package com.example.app

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog

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
}