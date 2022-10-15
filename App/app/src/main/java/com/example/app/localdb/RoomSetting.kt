package com.example.app.localdb

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RoomSetting {
    val dbList = mutableListOf<RoomExpDB>()
    lateinit var helper: RoomHelper

    fun init(context: Context){
        helper = Room.databaseBuilder(context, RoomHelper::class.java, "internalExpDb")
            .build()

        dbList.clear()
    }

    fun getDataFromServer(){
        expRoomDbBuild()
    }


    fun expRoomDbBuild() {
        //list를 먼저 받고, exp를 받아서 list에 exp가 있으면 name-count-exp순으로 앱 내부 db에 저장한다.
        //list받기
        var gson = GsonBuilder().setLenient().create()
        val nameList = mutableListOf<String>()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://jaeryurp.duckdns.org:40131/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val api = retrofit.create(GetIngredientAPI::class.java)
        val callList = api.getList("test")

        var resultJsonArray: JsonArray?

        callList.enqueue(object : Callback<JsonArray> {
            override fun onResponse(
                call: Call<JsonArray>,
                responseList: Response<JsonArray>
            ) {
                Log.d("List", "${responseList.body()}")
                resultJsonArray = responseList.body()
                if(nameList.isNotEmpty()){
                    nameList.clear()
                }
                val jsonArray = JSONTokener(resultJsonArray.toString()).nextValue() as JSONArray

                for (i in 0 until jsonArray.length()) {
                    val Sname = jsonArray.getJSONObject(i).getString("Tables_in_test")
                    nameList.add(Sname)
                }

                for (i in nameList) {
                    if (i == "temp_ingre" || i == "cookware") continue //filter
                    val callExp = api.getExp(i)
                    callExp.enqueue(object : Callback<JsonArray> {
                        override fun onResponse(
                            call: Call<JsonArray>,
                            responseExp: Response<JsonArray>
                        ) {
                            if (responseExp.body()!!.size() > 0) {
                                Log.d("EXP", "${responseExp.body()}")

                                val expArray = JSONTokener(
                                    responseExp.body().toString()
                                ).nextValue() as JSONArray

                                for (item in 0 until expArray.length()) {
                                    val count = expArray.getJSONObject(item).getString("count")
                                    val exp = expArray.getJSONObject(item).getString("expiration")
                                    val keyValue = expArray.getJSONObject(item).getString("keyvalue")

                                    Log.d("DB", "${i},${count},${exp},${keyValue}")
                                    insertData(RoomExpDB(i, count, exp, keyValue))
//                                    refreshAdapter()
                                }
                                refreshAdapter()
//                                val count = expArray.getJSONObject(expArray.length()-1).getString("count")
//                                val exp = expArray.getJSONObject(expArray.length()-1).getString("expiration")
//                                Log.d("DB","${i},${count},${exp}")
//                                insertData(RoomExpDB(i,count,exp))// 추가하는 것 까지 완성

                                // 이미 있으면 replace나 add 하지 않는 방법으로 적용할 예정
                            } else {
//                                Log.d("EXP Null","${responseExp.body()}")
                            }
                        }

                        override fun onFailure(call: Call<JsonArray>, t: Throwable) {

                        }
                    })
                }

//                listAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("List", "실패 : $t")
            }
        })

    }

    fun insertData(data: RoomExpDB) {
        CoroutineScope(Dispatchers.IO).launch {
            helper.roomExpDao().insert(data)
//            withContext(Dispatchers.Main){
//                //MainThread에서 할일
//            }
//            refreshAdapter()
        }
    }

    fun refreshAdapter() {
        //MainThread에서 안돌리려면 코루틴 안에 넣어야됨
        if (dbList.isNotEmpty()) {
            dbList.clear()
        }
        CoroutineScope(Dispatchers.IO).launch {
//            dbList.clear()
            dbList.addAll(helper.roomExpDao().getAll())
            //RoomDb가 존재하지 않으면 build하도록
            if (dbList.size == 0) {
                expRoomDbBuild()
            }
            withContext(Dispatchers.Main) {
//                dbAdapter.notifyDataSetChanged()
            }
        }
    }
}