package com.example.app.refrigerator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.example.app.MainActivity
import com.example.app.Pan
import com.example.app.R
import com.example.app.localdb.RoomSetting

class RefrigeratorStatus : AppCompatActivity() {
    //김민규 작업중

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refrigerator_status)

        RoomSetting().init(this)
        //조리도구 현황이동
        val pan= findViewById<ImageButton>(R.id.pan)
        pan.setOnClickListener {
            val intent = Intent(this, Pan::class.java)
            startActivity(intent)
        }

        // 임박/여유 5일 기준. 확인을 삭제.


        //하단바
        val home= findViewById<ImageButton>(R.id.home)
        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



    }
    /** when + btn pressed
     */
    fun onDialogClicked(view: View){
        val plusMenu = PlusMenu(this)
        plusMenu.show()
    }
}