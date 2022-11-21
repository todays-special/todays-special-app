package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.APIS.recipe
import com.example.app.APIS.recipeapi
import com.example.app.SearchAdap.SearchAdapter
import com.example.app.SearchAdap.SearchResult
import com.example.app.refrigerator.RefrigeratorStatus
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Search : AppCompatActivity() {

    lateinit var SearchAdapter: SearchAdapter
    var Result = ArrayList<SearchResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val rv = findViewById<RecyclerView>(R.id.searchRecycler)
        val search = findViewById<SearchView>(R.id.searchView)


        val home= findViewById<ImageButton>(R.id.home)
        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val refrigerator = findViewById<ImageButton>(R.id.refrigerator)
        refrigerator.setOnClickListener {
            val intent = Intent(this, RefrigeratorStatus::class.java)
            startActivity(intent)
        }

        var searchViewTextListener: SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                SearchAdapter.getFilter().filter(s)
                return false
            }
        }

        search.setOnQueryTextListener(searchViewTextListener)

        var gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://jaeryurp.duckdns.org:40131/")
//            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson)) //있으나마나한 코드...
            .build()
        val api = retrofit.create(recipeapi::class.java)
        val callResult = api.getRandom()
        var resultJsonArray: JsonArray?

        callResult.enqueue(object : Callback<JsonArray>{
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
            resultJsonArray = response.body()

                val jsonArray = JSONTokener(resultJsonArray.toString()).nextValue() as JSONArray


                insertRecipe(jsonArray)

                SearchAdapter = SearchAdapter(Result)
                rv.adapter = SearchAdapter
                rv.layoutManager = LinearLayoutManager(baseContext).apply{
                    orientation = RecyclerView.VERTICAL
                }


                SearchAdapter.itemClicks = object : SearchAdapter.ItemClick{
                    override fun onClick(view: View, position: Int) {
                        val intent = Intent(this@Search,RecipeRec::class.java)
                        intent.putExtra("BooleanName", Result[position].name)
                        intent.putExtra("key","image")
                        startActivity(intent)
                    }
                }
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("FeatTwo", "실패 : $t")
            }
        })


    }

    fun insertRecipe(jsonArray : JSONArray){
        for (i in 0 until jsonArray.length()) {
            val name = jsonArray.getJSONObject(i).getString("name")
            val chief = jsonArray.getJSONObject(i).getString("chief")
            var url = jsonArray.getJSONObject(i).getString("link")
            url = url.substring(url.lastIndexOf("=")+1)
            val getImage = "https://i1.ytimg.com/vi/"+ url + "/" + "maxresdefault.jpg"
            Result.add(SearchResult(getImage,name,chief))
        }
        Log.d("List", "$Result")
    }


}