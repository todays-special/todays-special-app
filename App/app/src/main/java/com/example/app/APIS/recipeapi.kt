package com.example.app.APIS

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface recipeapi {
    @GET("/show/show_korea") //한식
    fun getKorea(): Call<JsonArray>

    @GET("/show/show_japan.php")
    fun getJapan(): Call<JsonArray>

    @GET("/show/show_china.php")
    fun getChina(): Call<JsonArray>

    @GET("/show/show_western.php")
    fun getWestern(): Call<JsonArray>

    @GET("/show/show_snack.php")
    fun getSnack(): Call<JsonArray>

    @GET("/show/show_Random.php")
    fun getRandom(): Call<JsonArray>
}
