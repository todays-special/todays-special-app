package com.example.app.shopping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.refrigerator.ExpFineAdapter

class Shopping_tap : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_tap)

        val data = intent.getSerializableExtra("data") as MutableList<ShoppingView>
        Log.d("data","$data")

        val shopAdapter = ShoppingAdapter(data)
        val rv = findViewById<RecyclerView>(R.id.shopping_rv)
        rv.adapter = shopAdapter
        rv.layoutManager = GridLayoutManager(baseContext,2)




    }
}