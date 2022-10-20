package com.example.app.login

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface createAPI {
    @GET("/create/create_user.php?")
    fun createUser(
        @Query("Cname") Cname: String,
    ): Call<JsonArray>
}