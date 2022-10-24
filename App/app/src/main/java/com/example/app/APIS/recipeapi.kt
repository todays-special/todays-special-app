package com.example.app.APIS

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface recipeapi {
    @GET("/json.php") //한식
    fun getResult(): Call<JsonArray>

    @GET("/show/show_china.php")
    fun getChina(): Call<JsonArray>
}
