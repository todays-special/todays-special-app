package com.example.app


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class Find : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)

        setFrag(0)
    }

    private fun setFrag(fragNum : Int) {
        val ft = supportFragmentManager.beginTransaction()
        when(fragNum){
            0->{
                ft.replace(R.id.main_frame, Find_id()).commit()
            }
            1->{
                ft.replace(R.id.main_frame, Find_id()).commit()
            }
        }
    }
}