package com.example.app

import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
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
import com.example.app.RecAdapter.minusIngredient
import com.example.app.alert.Alert
import com.example.app.alert.AlertSetting

class Check(val recipe_ingredient: MutableList<EndCook>, context: Context) : Dialog(context) {

    lateinit var EndAdapter: EndCookAdapter

    var used = mutableListOf<minusIngredient>()

    private val prefs: SharedPreferences =
        context.getSharedPreferences("Ends_used", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        val rv = findViewById<RecyclerView>(R.id.check_recycler)
        val updateConfirm = findViewById<Button>(R.id.rec_done)
        val close = findViewById<ImageView>(R.id.close)

        for (i in recipe_ingredient.indices) {
            recipe_ingredient[i].usedIn.replace("[^0-9.]".toRegex(), "")
            val import = recipe_ingredient[i].usedIn
            setString("$i", "$import")
        }

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        EndAdapter = EndCookAdapter(recipe_ingredient, context)
        rv.adapter = EndAdapter
        rv.layoutManager = LinearLayoutManager(context).apply {
            orientation = RecyclerView.VERTICAL
        }
        Log.d("test", "$recipe_ingredient")

        close.setOnClickListener {
            dismiss()
        }

        updateConfirm.setOnClickListener {

            for (i in recipe_ingredient.indices) {
                val def = recipe_ingredient[i].usedIn
                val Name = recipe_ingredient[i].ingerdient
                val UseI = getString("$i", "$def")
                used.add(minusIngredient(Name, UseI))
            }
            Log.d("dismiss", "$used")
            //차감
            cancel()
        }


    }
}