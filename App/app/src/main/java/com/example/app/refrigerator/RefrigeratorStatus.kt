package com.example.app.refrigerator

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.app.MainActivity
import com.example.app.Pan
import com.example.app.R
import com.example.app.localdb.GetIngredientAPI
import com.example.app.localdb.RoomExpDB
import com.example.app.localdb.RoomHelper
import com.example.app.localdb.RoomSetting
import com.example.app.plusminus.ControlData
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_refrigerator_status.*
import kotlinx.android.synthetic.main.activity_refrigerator_status.check
import kotlinx.android.synthetic.main.activity_refrigerator_status.delete
import kotlinx.android.synthetic.main.activity_refrigerator_status.guide
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class RefrigeratorStatus : AppCompatActivity() {
    //김민규 작업중
    lateinit var fineAdapter: ExpFineAdapter
    lateinit var warningAdapter: ExpWarningAdapter
    lateinit var expiredAdapter: ExpExpiredAdapter
    lateinit var getList: MutableList<RoomExpDB>
    lateinit var mAlertDialog: AlertDialog

    val fineList = mutableListOf<ExpCount>()
    val warningList = mutableListOf<ExpCount>()
    val expiredList = mutableListOf<ExpCount>()

    val dbList = mutableListOf<RoomExpDB>()
    lateinit var helper: RoomHelper

    fun getRoomDb() {
        if (dbList.isNotEmpty()) {
            dbList.clear()
        }
        CoroutineScope(Dispatchers.IO).launch {
            //room에 있는 데이터 가져오기
            dbList.clear()
            dbList.addAll(helper.roomExpDao().getAll())
//            val ingreNames = mutableListOf<String>()
//            for (i in dbList){
//                ingreNames.add(i.name)
//            }
            //RoomDb가 존재하지 않으면 build하도록
            if (dbList.size == 0) {
                expRoomDbBuild()
            } else {
                //dbList로 가져온 데이터를 가공하는 곳
            }
            withContext(Dispatchers.Main) {
//                dbAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setCheck() {
        val prefs = getSharedPreferences("ref", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("ref_check", "checked")
        editor.apply()
    }

    private fun getCheck(): String? {
        val prefs = getSharedPreferences("ref", Context.MODE_PRIVATE)
        val value = prefs.getString("ref_check", "default")
        Log.d("ref_check", "$value")
        return value
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refrigerator_status)
        val rvFine = findViewById<RecyclerView>(R.id.rv_fine)
        val rvWarning = findViewById<RecyclerView>(R.id.rv_warning)
        val rvExpired = findViewById<RecyclerView>(R.id.rv_expired)
        val refresh = findViewById<SwipeRefreshLayout>(R.id.refresh)

//        delete.setOnClickListener {
//            guide.visibility = View.GONE
//        }

        val guideState = getCheck()
        if (guideState == "checked"){
            guide.visibility = View.GONE
        }else{
            delete.setOnClickListener {
                guide.visibility = View.GONE
                if (check.isChecked){
                    Log.d("RefCheck", "checked")
                    //다시 안뜨게
                    setCheck()
                    Toast.makeText(baseContext, "가이드가 다시 나오지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        refresh.setOnRefreshListener {
            CoroutineScope(Dispatchers.Main).launch {
                dbList.clear()
                expRoomDbBuild()
                delay(3000)
                refresh.isRefreshing = false
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            helper = Room.databaseBuilder(baseContext, RoomHelper::class.java, "internalExpDb")
                .build()
            dbList.clear()

            refreshAdapter()

            loading(1000)

            //오늘날짜 가져오기
            val today = Calendar.getInstance()
            val sf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            for (i in getList) {
                //5일 단위.
                val date = sf.parse(i.exp)
                val calcDate = (date.time - today.time.time) / (60 * 60 * 24 * 1000)
                if (calcDate < 5) {
                    if (calcDate < 0) {
                        expiredList.add(ExpCount("TEST", i.name, i.exp,i.count))
                    } else {
                        warningList.add(ExpCount("TEST", i.name, i.exp,i.count))
                    }
                } else {
                    fineList.add(ExpCount("TEST", i.name, i.exp,i.count))
                }
//                Log.d("calcDate:", "${i.name} : ${calcDate + 1} 남음")
            }

            fineAdapter = ExpFineAdapter(fineList)
            rvFine.adapter = fineAdapter
            rvFine.layoutManager = LinearLayoutManager(baseContext)

            warningAdapter = ExpWarningAdapter(warningList)
            rvWarning.adapter = warningAdapter
            rvWarning.layoutManager = LinearLayoutManager(baseContext)

            expiredAdapter = ExpExpiredAdapter(expiredList)
            rvExpired.adapter = expiredAdapter
            rvExpired.layoutManager = LinearLayoutManager(baseContext)

            withContext(Dispatchers.Main) {
                expiredAdapter.itemClick = object : ExpExpiredAdapter.ItemClick {
                    override fun onClick(view: View, position: Int) {
                        val exp = expiredList[position].exp
                        val name = expiredList[position].name
                        for (i in dbList) {
                            if (name == i.name && exp == i.exp) {
                                expiredList.removeAt(position)
                                deleteData(RoomExpDB(i.name, i.count, i.exp, i.keyValue))
                                ControlData().deleteData("test", name, exp)
                                break
                            }
                        }
                        // delete 할때 room이 초기화가 안됨. room을 날려야됨.
                        expiredAdapter.notifyItemRemoved(position)
                        fineAdapter.notifyDataSetChanged()
                        expiredAdapter.notifyDataSetChanged()
                        warningAdapter.notifyDataSetChanged()
                    }
                }
                fineAdapter.itemClick = object : ExpFineAdapter.ItemClick {
                    override fun onClick(view: View, position: Int) {
                        val exp = fineList[position].exp
                        val name = fineList[position].name
                        for (i in dbList) {
                            if (name == i.name && exp == i.exp) {
                                fineList.removeAt(position)
                                deleteData(RoomExpDB(i.name, i.count, i.exp, i.keyValue))
                                ControlData().deleteData("test", name, exp)
                                break
                            }
                        }
//                    ControlData().deleteData("test",name,exp)
                        fineAdapter.notifyItemRemoved(position)
                        fineAdapter.notifyDataSetChanged()
                        expiredAdapter.notifyDataSetChanged()
                        warningAdapter.notifyDataSetChanged()
                    }
                }
                warningAdapter.itemClick = object : ExpWarningAdapter.ItemClick {
                    override fun onClick(view: View, position: Int) {
                        val exp = warningList[position].exp
                        val name = warningList[position].name
                        for (i in dbList) {
                            if (name == i.name && exp == i.exp) {
                                warningList.removeAt(position)
                                deleteData(RoomExpDB(i.name, i.count, i.exp, i.keyValue))
                                ControlData().deleteData("test", name, exp)
                                break
                            }
                        }
//                    ControlData().deleteData("test",name,exp)
                        warningAdapter.notifyItemRemoved(position)
                        fineAdapter.notifyDataSetChanged()
                        expiredAdapter.notifyDataSetChanged()
                        warningAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
        //test code
//        var date = sf.parse("2022-10-05")
//        var calcuDate = (date.time - today.time.time) / (60 * 60 * 24 * 1000)


        //조리도구 현황이동
        val pan = findViewById<ImageButton>(R.id.pan)
        pan.setOnClickListener {
//            Log.d("getList:", "${getList}")
            val intent = Intent(this, Pan::class.java)
            startActivity(intent)
        }

        // 임박/여유 5일 기준. 확인을 삭제.
        val back = findViewById<ImageButton>(R.id.back)
        back.setOnClickListener {
            onBackPressed()
        }


        //하단바
        val home = findViewById<ImageButton>(R.id.home)
        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    suspend fun loading(time: Long) {
        val mDialogView =
            LayoutInflater.from(this@RefrigeratorStatus).inflate(R.layout.dialog_loading, null)
        val mBuilder = AlertDialog.Builder(this@RefrigeratorStatus)
            .setView(mDialogView)
            .setCancelable(false) //외부영역 터치해도 dismiss 안되게
        mAlertDialog = mBuilder.create()

        /** dialog 배경 투명하게 만들기*/
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        mAlertDialog.show()
        delay(time)
        mAlertDialog.dismiss()

        Log.d("getList:", "${getList}")
    }

    /** when + btn pressed
     */
    fun onDialogClicked(view: View) {
        val plusMenu = PlusMenu(this)
        plusMenu.show()
//        PlusMenu(this).mAlertDialog.findViewById<ImageButton>(R.id.plusclear).setOnClickListener {
//            Log.d("PlusMenu","Clicked")
//            fineAdapter.notifyDataSetChanged()
//            expiredAdapter.notifyDataSetChanged()
//            warningAdapter.notifyDataSetChanged()
//        }
        plusMenu.setOnDismissListener {
            Log.d("PlusMenu", "Clicked")
            expRoomDbBuild()
            fineAdapter.notifyDataSetChanged()
            expiredAdapter.notifyDataSetChanged()
            warningAdapter.notifyDataSetChanged()
        }
    }

    fun expRoomDbBuild() {
        //list를 먼저 받고, exp를 받아서 list에 exp가 있으면 name-count-exp순으로 앱 내부 db에 저장한다.
        //list받기
        var gson = GsonBuilder().setLenient().create()
        val nameList = mutableListOf<String>()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://jaeryurp.duckdns.org:40131/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val api = retrofit.create(GetIngredientAPI::class.java)
        val callList = api.getList("test")

        var resultJsonArray: JsonArray?

        callList.enqueue(object : Callback<JsonArray> {
            override fun onResponse(
                call: Call<JsonArray>,
                responseList: Response<JsonArray>
            ) {
                Log.d("List", "${responseList.body()}")
                resultJsonArray = responseList.body()
                if (nameList.isNotEmpty()) {
                    nameList.clear()
                }
                val jsonArray = JSONTokener(resultJsonArray.toString()).nextValue() as JSONArray
                for (i in 0 until jsonArray.length()) {
                    val Sname = jsonArray.getJSONObject(i).getString("Tables_in_test")
                    nameList.add(Sname)
                }
                for (i in nameList) {
                    if (i == "temp_ingre" || i == "cookware") continue //filter
                    val callExp = api.getExp(i)
                    callExp.enqueue(object : Callback<JsonArray> {
                        override fun onResponse(
                            call: Call<JsonArray>,
                            responseExp: Response<JsonArray>
                        ) {
                            if (responseExp.body()!!.size() > 0) {
                                Log.d("EXP", "${responseExp.body()}")

                                val expArray = JSONTokener(
                                    responseExp.body().toString()
                                ).nextValue() as JSONArray

                                for (item in 0 until expArray.length()) {
                                    val count = expArray.getJSONObject(item).getString("count")
                                    val exp = expArray.getJSONObject(item).getString("expiration")
                                    val keyValue =
                                        expArray.getJSONObject(item).getString("keyvalue")

                                    Log.d("DB", "${i},${count},${exp},${keyValue}")
                                    insertData(RoomExpDB(i, count, exp, keyValue))
//                                    refreshAdapter()
                                }
                                refreshAll()
//                                val count = expArray.getJSONObject(expArray.length()-1).getString("count")
//                                val exp = expArray.getJSONObject(expArray.length()-1).getString("expiration")
//                                Log.d("DB","${i},${count},${exp}")
//                                insertData(RoomExpDB(i,count,exp))// 추가하는 것 까지 완성

                                // 이미 있으면 replace나 add 하지 않는 방법으로 적용할 예정
                            } else {
//                                Log.d("EXP Null","${responseExp.body()}")
                            }
                        }

                        override fun onFailure(call: Call<JsonArray>, t: Throwable) {

                        }
                    })
                }

//                listAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("List", "실패 : $t")
            }
        })

    }

    fun insertData(data: RoomExpDB) {
        CoroutineScope(Dispatchers.IO).launch {
            helper.roomExpDao().insert(data)
//            withContext(Dispatchers.Main){
//                //MainThread에서 할일
//            }
//            refreshAdapter()
        }
    }

    fun deleteData(data: RoomExpDB) {
        CoroutineScope(Dispatchers.IO).launch {
            helper.roomExpDao().delete(data)
        }
    }

    fun refreshAdapter() {
        //MainThread에서 안돌리려면 코루틴 안에 넣어야됨
        if (dbList.isNotEmpty()) {
            dbList.clear()
        }
        CoroutineScope(Dispatchers.IO).launch {
//            dbList.clear()
            dbList.addAll(helper.roomExpDao().getAll())
            getList = dbList
            //RoomDb가 존재하지 않으면 build하도록
            if (dbList.size == 0) {
                expRoomDbBuild()
            }
            withContext(Dispatchers.Main) {
//                dbAdapter.notifyDataSetChanged()
            }
        }
    }

    fun refreshAll() {
        //MainThread에서 안돌리려면 코루틴 안에 넣어야됨
        if (dbList.isNotEmpty()) {
            dbList.clear()
        }
        CoroutineScope(Dispatchers.IO).launch {
//            dbList.clear()
            dbList.clear()
            dbList.addAll(helper.roomExpDao().getAll())
            delay(1000)
            withContext(Dispatchers.Main) {

                expiredList.clear()
                warningList.clear()
                fineList.clear()
                fineAdapter.notifyDataSetChanged()
                expiredAdapter.notifyDataSetChanged()
                warningAdapter.notifyDataSetChanged()
                val today = Calendar.getInstance()
                val sf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                for (i in dbList) {
                    //5일 단위.
                    val date = sf.parse(i.exp)
                    val calcDate = (date.time - today.time.time) / (60 * 60 * 24 * 1000)
                    if (calcDate < 5) {
                        if (calcDate < 0) {
                            expiredList.add(ExpCount("TEST", i.name, i.exp, i.count))
                        } else {
                            warningList.add(ExpCount("TEST", i.name, i.exp, i.count))
                        }
                    } else {
                        fineList.add(ExpCount("TEST", i.name, i.exp, i.count))
                    }
//                    Log.d("calcDate:", "${i.name} : ${calcDate + 1} 남음")
                }

                fineAdapter.notifyDataSetChanged()
                expiredAdapter.notifyDataSetChanged()
                warningAdapter.notifyDataSetChanged()
            }

        }
    }

}