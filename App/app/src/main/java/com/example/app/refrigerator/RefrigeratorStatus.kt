package com.example.app.refrigerator

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.MainActivity
import com.example.app.Pan
import com.example.app.R
import com.example.app.localdb.RoomExpDB
import com.example.app.localdb.RoomSetting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RefrigeratorStatus : AppCompatActivity() {
    //김민규 작업중
    lateinit var fineAdapter: ExpFineAdapter
    lateinit var warningAdapter: ExpWarningAdapter
    lateinit var getList: MutableList<RoomExpDB>
    lateinit var mAlertDialog: AlertDialog

    val fineList = mutableListOf<Exp>()
    val warningList = mutableListOf<Exp>()
    val expiredList = mutableListOf<Exp>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refrigerator_status)
        val rvFine = findViewById<RecyclerView>(R.id.rv_fine)
        val rvWarning = findViewById<RecyclerView>(R.id.rv_warning)

        CoroutineScope(Dispatchers.Main).launch {
            RoomSetting().run {
                init(context = this@RefrigeratorStatus)
                getList = dbList
            }

            val mDialogView = LayoutInflater.from(this@RefrigeratorStatus).inflate(R.layout.dialog_loading, null)
            val mBuilder = AlertDialog.Builder(this@RefrigeratorStatus)
                .setView(mDialogView)
                .setCancelable(false)
            mAlertDialog = mBuilder.create()

            //dialog 배경 투명하게 만들기
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

            mAlertDialog.show()
            delay(2000)
            mAlertDialog.dismiss()

            Log.d("getList:", "${getList}")

            //오늘날짜 가져오기
            val today = Calendar.getInstance()
            val sf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            for (i in getList) {
                //5일 단위.
                val date = sf.parse(i.exp)
                val calcDate = (date.time - today.time.time) / (60 * 60 * 24 * 1000)
                if(calcDate < 5){
                    warningList.add(Exp("TEST",i.name,i.exp))
                    if (calcDate < 0){

                    }
                }else{
                    fineList.add(Exp("TEST",i.name,i.exp))
                }
                Log.d("calcDate:", "${i.name} : ${calcDate + 1} 남음")
            }

            fineAdapter = ExpFineAdapter(fineList)
            rvFine.adapter = fineAdapter
            rvFine.layoutManager = LinearLayoutManager(baseContext)

            warningAdapter = ExpWarningAdapter(warningList)
            rvWarning.adapter = warningAdapter
            rvWarning.layoutManager = LinearLayoutManager(baseContext)
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



        //하단바
        val home = findViewById<ImageButton>(R.id.home)
        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    /** when + btn pressed
     */
    fun onDialogClicked(view: View) {
        val plusMenu = PlusMenu(this)
        plusMenu.show()
    }
}