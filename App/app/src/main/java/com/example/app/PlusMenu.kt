package com.example.app

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageButton
import java.util.Calendar

class PlusMenu(context: Context) : Dialog(context) {

    lateinit var date: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plus_menu)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val calendar = findViewById<ImageButton>(R.id.calendar)
        calendar.setOnClickListener {

            val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_calendar, null)
            val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)
                .setTitle("유효기간설정")

            val mAlertDialog = mBuilder.create()
            mAlertDialog.show()
            mAlertDialog.findViewById<CalendarView>(R.id.dialog_calendar_item)
                .setOnDateChangeListener { _, year, month, dayOfMonth ->
                    var day: String = ""
                    if (month > 8) {
                        day = if (dayOfMonth < 9) {
                            "0$dayOfMonth"
                        } else {
                            "$dayOfMonth"
                        }
                        date = "$year-${month + 1}-$day"
                    } else {
                        day = if (dayOfMonth < 9) {
                            "0$dayOfMonth"
                        } else {
                            "$dayOfMonth"
                        }
                        date = "$year-0${month + 1}-$day"
                    }
//                    Log.d("date", "$date")
                    //여기서 받은 date를 입력하면 됨.
                }
            mAlertDialog.findViewById<Button>(R.id.btn_ok).setOnClickListener {
                Log.d("date", "$date")
                dismiss()
            }
        }
    }
}