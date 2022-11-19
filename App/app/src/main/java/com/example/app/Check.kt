package com.example.app

import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.RecAdapter.EndCook
import com.example.app.RecAdapter.EndCookAdapter
import com.example.app.alert.Alert
import com.example.app.alert.AlertSetting

class Check(val recipe_ingredient: MutableList<EndCook>, context: Context) : Dialog(context) {

    lateinit var EndAdapter: EndCookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        val rv = findViewById<RecyclerView>(R.id.check_recycler)
        val updateConfirm = findViewById<Button>(R.id.rec_done)
        val close = findViewById<ImageView>(R.id.close)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        EndAdapter = EndCookAdapter(recipe_ingredient)
        rv.adapter = EndAdapter
        rv.layoutManager = LinearLayoutManager(context).apply {
            orientation = RecyclerView.VERTICAL
        }
        Log.d("test", "$recipe_ingredient")

        close.setOnClickListener {
            dismiss()
        }

        updateConfirm.setOnClickListener {
            //차감
            cancel()
        }


    }
}