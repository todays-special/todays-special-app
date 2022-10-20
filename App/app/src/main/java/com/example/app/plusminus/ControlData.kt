package com.example.app.plusminus

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ControlData {
    fun insertData(Iname: String, Iingredient: String, Icnt: String, Idate: String) {
        var gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://jaeryurp.duckdns.org:40131/")
//            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson)) //있으나마나한 코드...
            .build()
        val api = retrofit.create(InsertDeleteAPI::class.java)

        val insertResult = api.insert(Iname, Iingredient, Icnt, Idate)

        insertResult.enqueue(object : Callback<JsonArray> {
            override fun onResponse(
                call: Call<JsonArray>,
                response: Response<JsonArray>
            ) {
                Log.d("insert", "성공 : ${response.body()}")
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("insert", "실패 : $t")
            }
        })
    }
}