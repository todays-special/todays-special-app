package com.example.app.shopping

import com.bumptech.glide.annotation.Excludes
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface ShoppingAPI {
    @GET("search/{type}")
    fun getSearchResult(
        @Header("X-Naver-Client-Id") id: String?,
        @Header("X-Naver-Client-Secret") pw: String?,
        @Path("type") type: String?,
        @Query("query") query: String?,
        @Query("exclude") exclude: String?,
    ): Call<shopping>
}