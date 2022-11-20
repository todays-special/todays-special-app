package com.example.app

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.MainTop.MainTopAdapter
import com.example.app.RecAdapter.EndCook
import com.example.app.RecAdapter.EndCookAdapter
import com.example.app.RecAdapter.SharedPer

class Check(val recipe_ingredient:MutableList<EndCook>,context: Context) : Dialog(context) {

    lateinit var EndAdapter: EndCookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)
        val rv = findViewById<RecyclerView>(R.id.check_recycler)

        for(i in recipe_ingredient.indices){
            recipe_ingredient[i].usedIn.replace("[^0-9]".toRegex(),"")
            MyApplication.Ingerd.setString("$i",recipe_ingredient[i].ingerdient)
            MyApplication.Used.setString("$i",recipe_ingredient[i].usedIn)
        }

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        EndAdapter = EndCookAdapter(recipe_ingredient)
        rv.adapter = EndAdapter
        rv.layoutManager = LinearLayoutManager(context).apply{
            orientation = RecyclerView.VERTICAL
        }

        val change_btn = findViewById<Button>(R.id.check_change)
        change_btn.setOnClickListener{
            Toast.makeText(context,"수정되었습니다.",Toast.LENGTH_SHORT).show()
        }


        val check_btn = findViewById<Button>(R.id.check_ok)
        check_btn.setOnClickListener{
            for(i in recipe_ingredient.indices){
                recipe_ingredient[i].ingerdient = MyApplication.Ingerd.getString("$i",recipe_ingredient[i].ingerdient)
                recipe_ingredient[i].usedIn = MyApplication.Used.getString("$i",recipe_ingredient[i].usedIn)
            }
            dismiss()
        }

    }
}