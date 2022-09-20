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
import android.view.View
import android.widget.*

class PlusMenu(context: Context) : Dialog(context) {

    lateinit var date: String
    lateinit var mAlertDialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plus_menu)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        var spinner = findViewById<Spinner>(R.id.spinner)
        var spinner2 = findViewById<Spinner>(R.id.spinner2)
        spinner.adapter = ArrayAdapter.createFromResource(this.context , R.array.test, android.R.layout.simple_spinner_item)
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                    1 -> {
                        spinner2.adapter = ArrayAdapter.createFromResource( context , R.array.test_vegetable, android.R.layout.simple_spinner_item)
                    }
                    2 -> {
                        spinner2.adapter = ArrayAdapter.createFromResource( context , R.array.test_fruit, android.R.layout.simple_spinner_item)
                    }

                    3 -> {
                        spinner2.adapter = ArrayAdapter.createFromResource( context , R.array.test_meat, android.R.layout.simple_spinner_item)
                    }
                    4 -> {
                        spinner2.adapter = ArrayAdapter.createFromResource( context , R.array.test_fish, android.R.layout.simple_spinner_item)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        val calendar = findViewById<ImageButton>(R.id.calendar)
        calendar.setOnClickListener {

            val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_calendar, null)
            val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)
                .setTitle("유효기간설정")

            mAlertDialog = mBuilder.create()
            mAlertDialog.show()
            mAlertDialog.findViewById<CalendarView>(R.id.dialog_calendar_item)
                .setOnDateChangeListener { _, year, month, dayOfMonth ->
                    var day: String = ""
                    if (month > 8) {
                        day = if (dayOfMonth < 10) {
                            "0$dayOfMonth"
                        } else {
                            "$dayOfMonth"
                        }
                        date = "$year-${month + 1}-$day"
                    } else {
                        day = if (dayOfMonth < 10) {
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
                mAlertDialog.dismiss()
            }
        }
    }

}