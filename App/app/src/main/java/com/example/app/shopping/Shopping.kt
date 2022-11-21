package com.example.app.shopping

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.MainTop.MainTopAdapter
import com.example.app.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_shopping2.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Shopping : AppCompatActivity() {

    var clientId = "YR1nlA7YckDaosOJGVUF" //애플리케이션 클라이언트 아이디값";
    var clientSecret = "eWrj0Qh_NU" //애플리케이션 클라이언트 시크릿값";
    val url = "https://openapi.naver.com/v1/"
    lateinit var mAlertDialog: AlertDialog
    val shoppingItems = mutableListOf<ShoppingView>()

    suspend fun loading(time: Long) {
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.dialog_loading, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setCancelable(false) //외부영역 터치해도 dismiss 안되게
        mAlertDialog = mBuilder.create()

        /** dialog 배경 투명하게 만들기*/
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        mAlertDialog.show()
        delay(time)
        mAlertDialog.dismiss()
    }

    override fun onResume() {
        super.onResume()
        shoppingItems.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping2)
        val et = findViewById<TextView>(R.id.editText6)
        val btn = findViewById<ImageView>(R.id.search)

        val values = intent.getStringExtra("igred")

        val valueList = values?.split(", ") as MutableList<String>
        val textAdapter = ShoppingTextAdapter(valueList)
        recommendRv.adapter = textAdapter
        recommendRv.layoutManager = LinearLayoutManager(baseContext).apply {
            orientation = RecyclerView.HORIZONTAL
            textAdapter.notifyDataSetChanged()
        }

        textAdapter.itemClick = object : ShoppingTextAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                //여기서 재료랑 밑에 사진 맵핑
                val name = valueList[position]
                et.text= name
                Log.d("ShoppingText", "$name")
            }
        }


        btn.setOnClickListener {
            if (et.text.isNullOrEmpty()) {
                Toast.makeText(this,"검색할 단어가 없습니다", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, Shopping_tap::class.java)

                CoroutineScope(Dispatchers.Main).launch {
                    val keyword = et.text.toString()
                    var gson = GsonBuilder().setLenient().create()
                    val retrofit = Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
                    val api = retrofit.create(ShoppingAPI::class.java)
                    val result = api.getSearchResult(
                        clientId,
                        clientSecret,
                        "shop.json",
                        keyword,
                        exclude = "used:cbshop"
                    )
                    result.enqueue(object : Callback<shoppingDC> {
                        override fun onResponse(
                            call: Call<shoppingDC>,
                            response: Response<shoppingDC>
                        ) {
                            Log.d("naver", "성공 : ${response.body()}")
//                        val item = response.body()?.items?.get(0)
                            for (i in response.body()!!.items) {
                                shoppingItems.add(
                                    ShoppingView(
                                        i.title,
                                        i.lprice,
                                        i.image,
                                        i.mallName ?: i.brand,
                                        i.link
                                    )
                                )
                            }
                        }

                        override fun onFailure(call: Call<shoppingDC>, t: Throwable) {
                            Log.d("naver", "실패 : $t")
                        }
                    })
                    loading(1000)
                    intent.putExtra("data", shoppingItems as java.io.Serializable)
                    startActivity(intent)
                    Log.d("Shopping", "${shoppingItems.size} $shoppingItems")
                }
            }
        }

        var gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val api = retrofit.create(ShoppingAPI::class.java)
        val result =
            api.getSearchResult(clientId, clientSecret, "shop.json", "우유", exclude = "used:cbshop")
        result.enqueue(object : Callback<shoppingDC> {
            override fun onResponse(
                call: Call<shoppingDC>,
                response: Response<shoppingDC>
            ) {
                Log.d("naver", "성공 : ${response.body()}")
                val item = response.body()?.items?.get(0)
//                Glide.with(iv)
//                    .load(item?.image)
//                    .centerCrop()
//                    .into(iv)
//                tv.text = item?.link
            }

            override fun onFailure(call: Call<shoppingDC>, t: Throwable) {
                Log.d("naver", "실패 : $t")
            }
        })

//        tv.setOnClickListener {
//
//        }
    }


}