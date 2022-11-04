package com.example.app

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.MainTop.MainTopAdapter
import com.example.app.RecAdapter.EndCook
import com.example.app.RecAdapter.EndCookAdapter

class Check(val recipe_ingredient:MutableList<EndCook>,context: Context) : Dialog(context) {

    lateinit var EndAdapter: EndCookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)
        val rv = findViewById<RecyclerView>(R.id.check_recycler)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        EndAdapter = EndCookAdapter(recipe_ingredient)
        rv.adapter = EndAdapter
        rv.layoutManager = LinearLayoutManager(context).apply{
            orientation = RecyclerView.VERTICAL
        }
        Log.d("test", "$recipe_ingredient")
    }
}