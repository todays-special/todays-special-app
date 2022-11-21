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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.app.MainTop.MainTop
import com.example.app.RecAdapter.EndCook
import com.example.app.RecAdapter.EndCookAdapter
import com.example.app.RecAdapter.minusIngredient
import com.example.app.alert.Alert
import com.example.app.alert.AlertSetting
import com.example.app.localdb.RoomExpDB
import com.example.app.localdb.RoomHelper
import com.example.app.recipeapi.UpdateAPI
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

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

    private lateinit var auth: FirebaseAuth
    val dbList = mutableListOf<RoomExpDB>()
    lateinit var helper: RoomHelper
    val dateCheck = mutableListOf<String>()
    val mainIngList = mutableListOf<MainTop>()
    var comparedDate: String = ""

    fun compareDateItem(Uingredient: String) {
        helper = Room.databaseBuilder(context, RoomHelper::class.java, "internalExpDb")
            .build()
        if (dbList.isNotEmpty()) {
            dbList.clear()
        }
        CoroutineScope(Dispatchers.IO).launch {
            //로컬에 있는 데이터 가져오기
            dbList.addAll(helper.roomExpDao().getAll())
            //RoomDb가 존재하지 않으면 build하도록
            if (dbList.size == 0) {

            } else {
                //dbList로 가져온 데이터를 가공하는 곳
                val sf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                Log.d("Update", "compared")
                for (i in dbList) {
                    //날짜 비교해서 제일 빠른거 넣어두는 것
                    if (i.name == Uingredient) {
                        if (i.name in dateCheck) {
                            // 이미 재료가 들어가있으니 날짜 비교
                            val firstDate: Date = sf.parse(comparedDate) as Date
                            val secondDate: Date = sf.parse(i.exp) as Date
                            // 제일 오래된 재료부터 찾아서 긁어옴
                            when {
                                firstDate.after(secondDate) -> {
                                    comparedDate = i.exp
                                    Log.d("Update", "$comparedDate is after ${i.exp}")
                                }
                                firstDate.before(secondDate) -> {
                                    Log.d("Update", "$comparedDate is before ${i.exp}")
                                }
                                firstDate == secondDate -> {
                                    Log.d("Update", "Both dates are equal")
                                }
                            }
                        } else {
                            dateCheck.add(i.name)
                            comparedDate = i.exp
                        }
                    }
                }
            }
            withContext(Dispatchers.Main) {
//                dbAdapter.notifyDataSetChanged()
            }
        }
    }

    fun updateIngredient(Uingredient: String, Ucnt: String, Udate: String) {
        compareDateItem(Uingredient)

        val Uname = auth.currentUser?.uid as String

        var gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://jaeryurp.duckdns.org:40131/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val api = retrofit.create(UpdateAPI::class.java)
//        val callResult = api.update(Uname, Uingredient, Ucnt, Udate)
        val callResult = api.update("test", Uingredient, Ucnt, Udate)

        callResult.enqueue(object : Callback<JsonArray> {
            override fun onResponse(
                call: Call<JsonArray>,
                response: Response<JsonArray>
            ) {
                Log.d("Update", "값이 수정되었습니다")
                Toast.makeText(
                    context, "값이 수정되었습니다",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("Update", "실패 : $t")
            }
        })
    }
}