package com.example.app

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.app.MainTop.MainTop
import com.example.app.MainTop.MainTopAdapter
import com.example.app.localdb.RoomExpDB
import com.example.app.localdb.RoomHelper
import com.example.app.login.SettingUserName
import com.example.app.refrigerator.Exp
import com.example.app.refrigerator.ExpExpiredAdapter
import com.example.app.refrigerator.ExpFineAdapter
import com.example.app.refrigerator.RefrigeratorStatus
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


const val testUrl =
    "https://img.danawa.com/prod_img/500000/616/833/img/3833616_1.jpg?shrink=330:330&_v=20170329122809"

class MainActivity : AppCompatActivity() {

    //    private fun getName(key: String): String? {
//        val prefs = getSharedPreferences(New_customer.sharedPrefFileName, Context.MODE_PRIVATE)
//        val value = prefs.getString(key, " ") ?: "User+${Random(10000).nextInt()}"
//        Log.d("name", "$value")
//        return value
//    }
    val dbList = mutableListOf<RoomExpDB>()
    lateinit var helper: RoomHelper
    val mainIngList = mutableListOf<MainTop>()
    lateinit var mainAdapter: MainTopAdapter

    fun getRoomDb() {
        helper = Room.databaseBuilder(baseContext, RoomHelper::class.java, "internalExpDb")
            .build()
        if (dbList.isNotEmpty()) {
            dbList.clear()
        }
        CoroutineScope(Dispatchers.IO).launch {
            //room에 있는 데이터 가져오기
            dbList.addAll(helper.roomExpDao().getAll())
            //RoomDb가 존재하지 않으면 build하도록
            if (dbList.size == 0) {

            } else {
                //dbList로 가져온 데이터를 가공하는 곳
                val today = Calendar.getInstance()
                val sf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

                for (i in dbList) {
                    //5일 단위.
                    val date = sf.parse(i.exp)
                    val calcDate = (date.time - today.time.time) / (60 * 60 * 24 * 1000)
                    if (calcDate < 5) {
                        mainIngList.add(MainTop(testUrl, i.name))
                    }
                    Log.d("Main", i.name + " " + i.exp +" "+ i.count)
                }
            }
            withContext(Dispatchers.Main) {
//                dbAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_App)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rv = findViewById<RecyclerView>(R.id.main_rv)
        getRoomDb()

        delete.setOnClickListener {
            layout2.visibility = View.GONE
        }

        //상단 재료 바
        mainAdapter = MainTopAdapter(mainIngList)
        rv.adapter = mainAdapter
        rv.layoutManager = LinearLayoutManager(baseContext).apply {
            orientation = RecyclerView.HORIZONTAL
        }
        // 리사이클러뷰 아이템을 클릭하면 여기가 호출됨
        mainAdapter.itemClick = object : MainTopAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                //여기서 재료랑 밑에 사진 맵핑
                val itemName = mainIngList[position].name
                Toast.makeText(baseContext, "$itemName", Toast.LENGTH_SHORT).show()
                Log.d("MainClick", "$itemName")
            }
        }

        val imageView4 = findViewById<ImageView>(R.id.imageView4)
        val imageView5 = findViewById<ImageView>(R.id.imageView5)
        val imageView3 = findViewById<ImageView>(R.id.imageView3)

        val bell = findViewById<ImageButton>(R.id.bell)
        bell.setOnClickListener {
            val intent = Intent(this, Notice::class.java)
            startActivity(intent)
        }

        main_name.text = "${SettingUserName().getName("name", this).toString()}의 냉장고"


        //하단바 이동
        val refrigerator = findViewById<ImageButton>(R.id.refrigerator)
        refrigerator.setOnClickListener {
            val intent = Intent(this, RefrigeratorStatus::class.java)
            startActivity(intent)
        }

        val suljung = findViewById<ImageButton>(R.id.suljung)
        suljung.setOnClickListener {
            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }

        //레시피 이동 , 한식
        val korean = findViewById<ImageButton>(R.id.korean)
        korean.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            startActivity(intent)
        }
        //레시피 이동 , 일식
        val japan = findViewById<ImageButton>(R.id.japan)
        japan.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            startActivity(intent)
        }
        //레시피 이동 , 양식
        val american = findViewById<ImageButton>(R.id.american)
        american.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            startActivity(intent)
        }
        //레시피 이동 , 중식
        val china = findViewById<ImageButton>(R.id.china)
        china.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            startActivity(intent)
        }
        //레시피 이동 , 분식
        val bunsik = findViewById<ImageButton>(R.id.bunsik)
        bunsik.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            startActivity(intent)
        }
        //레시피 이동 , 육류
        val meat = findViewById<ImageButton>(R.id.meat)
        meat.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            startActivity(intent)
        }
        //레시피 이동, 해산물
        val seafood = findViewById<ImageButton>(R.id.seafood)
        seafood.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            startActivity(intent)
        }
        //레시피 이동, 랜덤
        val random = findViewById<ImageButton>(R.id.random)
        random.setOnClickListener {
            val intent = Intent(this, RecipeRec::class.java)
            startActivity(intent)
        }

//        val name2 = findViewById<ImageButton>(R.id.name2)
//        name2.setOnClickListener {
//            imageView4.visibility = View.INVISIBLE
//            imageView5.visibility = View.INVISIBLE
//            imageView3.visibility = View.VISIBLE
//        }
//
//        val name1 = findViewById<ImageButton>(R.id.main_item_img)
//        name1.setOnClickListener {
//            imageView4.visibility = View.VISIBLE
//            imageView5.visibility = View.INVISIBLE
//            imageView3.visibility = View.INVISIBLE
//
//        }
//
//        val name3 = findViewById<ImageButton>(R.id.name3)
//        name3.setOnClickListener {
//            imageView4.visibility = View.INVISIBLE
//            imageView5.visibility = View.VISIBLE
//            imageView3.visibility = View.INVISIBLE
//
//        }
    }

    override fun onResume() {
        super.onResume()
        main_name.text = "${SettingUserName().getName("name", this).toString()}의 냉장고"
    }
}