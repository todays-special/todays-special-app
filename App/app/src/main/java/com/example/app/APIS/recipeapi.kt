package com.example.app.APIS

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface recipeapi {
    @GET("/show/show_temptable.php")
    fun getRefrigeTable(

    ): Call<JsonArray>


    @GET("/json.php")
    fun getResult(

    ): Call<JsonArray>
}
