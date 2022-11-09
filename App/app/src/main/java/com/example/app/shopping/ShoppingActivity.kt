package com.example.app.shopping

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.app.MainTop.testUrl
import com.example.app.R
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShoppingActivity : AppCompatActivity() {
    var clientId = "YR1nlA7YckDaosOJGVUF" //애플리케이션 클라이언트 아이디값";
    var clientSecret = "eWrj0Qh_NU" //애플리케이션 클라이언트 시크릿값";
    val url = "https://openapi.naver.com/v1/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        val tv = findViewById<TextView>(R.id.item_tv)
        val iv = findViewById<ImageView>(R.id.item_iv)
        val et = findViewById<TextView>(R.id.item_et)
        val btn = findViewById<TextView>(R.id.item_btn)

        btn.setOnClickListener {
            val keyword = et.text.toString()
            var gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            val api = retrofit.create(ShoppingAPI::class.java)
            val result = api.getSearchResult(clientId, clientSecret,"shop.json",keyword,exclude="used:cbshop")
            result.enqueue(object : Callback<shopping> {
                override fun onResponse(
                    call: Call<shopping>,
                    response: Response<shopping>
                ) {
                    Log.d("naver", "성공 : ${response.body()}")
                    val item = response.body()?.items?.get(0)
                    Glide.with(iv)
                        .load(item?.image)
                        .centerCrop()
                        .into(iv)
                    tv.text = item?.link
                }

                override fun onFailure(call: Call<shopping>, t: Throwable) {
                    Log.d("naver", "실패 : $t")
                }
            })
        }

        var gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val api = retrofit.create(ShoppingAPI::class.java)
        val result = api.getSearchResult(clientId, clientSecret,"shop.json","우유",exclude="used:cbshop")
        result.enqueue(object : Callback<shopping> {
            override fun onResponse(
                call: Call<shopping>,
                response: Response<shopping>
            ) {
                Log.d("naver", "성공 : ${response.body()}")
                val item = response.body()?.items?.get(0)
                Glide.with(iv)
                    .load(item?.image)
                    .centerCrop()
                    .into(iv)
                tv.text = item?.link
            }

            override fun onFailure(call: Call<shopping>, t: Throwable) {
                Log.d("naver", "실패 : $t")
            }
        })

        tv.setOnClickListener {

        }
    }
}