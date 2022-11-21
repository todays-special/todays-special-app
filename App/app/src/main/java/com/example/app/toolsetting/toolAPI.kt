package com.example.app.toolsetting

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface toolAPI {
    @GET("/update/update_cookware.php?")
    fun update(
        @Query("Uname") Uname: String,
        @Query("cookware") cookware: String,
        @Query("exist") exist: String,
    ): Call<JsonArray>
}