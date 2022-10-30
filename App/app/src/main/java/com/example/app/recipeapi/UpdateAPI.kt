package com.example.app.recipeapi

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UpdateAPI {
    // http://jaeryurp.duckdns.org:40131/update/update_ingredient.php?Uname=test&Uingredient=fruit_pear&Ucnt=3&Udate=2022-08-12
    @GET("/update/update_ingredient.php?")
    fun update(
        @Query("Uname") Uname: String,
        @Query("Uingredient") Uingredient: String,
        @Query("Ucnt") Ucnt: String,
        @Query("Udate") Udate: String
    ): Call<JsonArray>
}