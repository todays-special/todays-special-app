package com.example.app.plusminus

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface InsertDeleteAPI {
    @GET("/insert/insert_ingredient.php?")
    fun insert(
        @Query("Iname") Iname: String,
        @Query("Iingredient") Iingredient: String,
        @Query("Icnt") Icnt: String,
        @Query("Idate") Idate: String,
        ): Call<JsonArray>

    @GET("/delete/deleteingredient.php?")
    fun delete(
        @Query("Dname") Dname: String,
        @Query("Dingredient") Dingredient: String,
        @Query("Ddate") Ddate: String,
        ): Call<JsonArray>
}