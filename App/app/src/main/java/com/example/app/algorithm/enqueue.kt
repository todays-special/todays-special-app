package com.example.app.algorithm

import android.util.Log
import com.example.app.APIS.recipe
import com.example.app.APIS.recipeapi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class enqueue {

    /** Api 임시 테이블 **/
    fun getApiTempJson(): JsonArray? {
        return try {
            var gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl("http://jaeryurp.duckdns.org:40131/")
                .addConverterFactory(GsonConverterFactory.create(gson)) //있으나마나한 코드...
                .build()

            val api = retrofit.create(recipeapi::class.java)
            val callResult = api.getResult()

            callResult.execute().body()
        } catch (err: Exception) {
            null;
        }
    }

   public fun getRecipeArray(): MutableList<recipe>? {
        var resultJsonArray = getApiTempJson() ?: return null;

        val items = mutableListOf<recipe>()
        val jsonArray = JSONTokener(resultJsonArray.toString()).nextValue() as JSONArray

        for (i in 0 until jsonArray.length()) {
            val convertRecipe: recipe = Gson().fromJson(jsonArray.getJSONObject(i).toString(), recipe::class.java)
            val match = 0
            val ingredient = jsonArray.getJSONObject(i).getString("ingredient")
            val list = ingredient.substring(1, ingredient.length - 1).split(", ").toList()
            convertRecipe.matchCount = match
            convertRecipe.Ingredient = list
            items.add(convertRecipe)

//
//            val name = jsonArray.getJSONObject(i).getString("name")
//            val chief = jsonArray.getJSONObject(i).getString("chief")
//            val step1 = jsonArray.getJSONObject(i).getString("step1")
//            val step2 = jsonArray.getJSONObject(i).getString("step2")
//            val step3 = jsonArray.getJSONObject(i).getString("step3")
//            val step4 = jsonArray.getJSONObject(i).getString("step4")
//            val step5 = jsonArray.getJSONObject(i).getString("step5")
//            val step6 = jsonArray.getJSONObject(i).getString("step6")
//            val step7 = jsonArray.getJSONObject(i).getString("step7")
//            val step8 = jsonArray.getJSONObject(i).getString("step8")
//            val ingredient = jsonArray.getJSONObject(i).getString("ingredient")
//            val url = jsonArray.getJSONObject(i).getString("link")
//            val match = 0
//            val list = ingredient.substring(1, ingredient.length - 1).split(", ").toList()
//            items.add(
//                recipe(  name, list,  chief, step1, step2,  step3,   step4,   step5,  step6, step7,     step8,     url,  match )
//            )
        }
        return items;
    }
}


