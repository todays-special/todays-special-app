package com.example.app.recipeapi

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.app.MainTop.MainTop
import com.example.app.MainTop.MainTopAdapter
import com.example.app.R
import com.example.app.localdb.RoomExpDB
import com.example.app.localdb.RoomHelper
import com.example.app.testUrl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_update_test.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class UpdateTestActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    val dbList = mutableListOf<RoomExpDB>()
    lateinit var helper: RoomHelper
    val dateCheck = mutableListOf<String>()
    val mainIngList = mutableListOf<MainTop>()
    var comparedDate: String = ""

    fun compareDateItem(Uingredient: String) {
        helper = Room.databaseBuilder(baseContext, RoomHelper::class.java, "internalExpDb")
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_test)
        auth = Firebase.auth

        btn.setOnClickListener {
            compareDateItem(name.text.toString())

            Log.d("compared", "$comparedDate")
            // 현재 비동기라서 comparedDateItem이 실행되는 동안에 updateIngredient 함수도 실행돼서 comparedDate에 null값이 들어감.
            // 미리 date값을 계산해줘야됨
            updateIngredient(name.text.toString(), cnt.text.toString(), comparedDate)
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
                    baseContext, "값이 수정되었습니다",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("Update", "실패 : $t")
            }
        })
    }
}