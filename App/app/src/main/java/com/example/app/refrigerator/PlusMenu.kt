package com.example.app.refrigerator

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.app.IngredientData
import com.example.app.R

class PlusMenu(context: Context) : Dialog(context) {

    lateinit var date: String
    lateinit var mAlertDialog: AlertDialog
    var sortName: String = ""
    var nameValue: String = ""
    var volume: String = ""
    var exp: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plus_menu)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        var spinner = findViewById<Spinner>(R.id.spinner)
        var spinner2 = findViewById<Spinner>(R.id.spinner2)

//        spinner.adapter = ArrayAdapter.createFromResource(this.context , R.array.test, android.R.layout.simple_spinner_item)
        spinner.adapter = ArrayAdapter(this.context ,android.R.layout.simple_spinner_item,
            IngredientData().spinnerList)

        spinner.setSelection(0)
        sortName = IngredientData().spinnerList[0]
        spinner2.adapter = ArrayAdapter(context ,android.R.layout.simple_spinner_item,
            IngredientData().spinnerGrain)
//        "쌀/곡식","견과","과일","채소","버섯","육류","유제품","해산물","소스","양념","허브","통조림","술"
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                    0 ->{
                        //TODO spinner 아이템 가져오기
                        spinner2.adapter = ArrayAdapter(context ,android.R.layout.simple_spinner_item,
                            IngredientData().spinnerGrain)
                        sortName = IngredientData().spinnerList[0]
                    }
                    1 -> {
                        spinner2.adapter = ArrayAdapter(context ,android.R.layout.simple_spinner_item,
                            IngredientData().spinnerNut)
                        sortName = IngredientData().spinnerList[1]
                    }
                    2 -> {
                        spinner2.adapter = ArrayAdapter(context ,android.R.layout.simple_spinner_item,
                            IngredientData().spinnerFruit)
                        sortName = IngredientData().spinnerList[2]
                    }
                    3 -> {
                        spinner2.adapter = ArrayAdapter(context ,android.R.layout.simple_spinner_item,
                            IngredientData().spinnerVegetable)
                        sortName = IngredientData().spinnerList[3]
                    }
                    4 -> {
                        spinner2.adapter = ArrayAdapter(context ,android.R.layout.simple_spinner_item,
                            IngredientData().spinnerMushroom)
                        sortName = IngredientData().spinnerList[4]
                    }
                    5 -> {
                        spinner2.adapter = ArrayAdapter(context ,android.R.layout.simple_spinner_item,
                            IngredientData().spinnerMeat)
                        sortName = IngredientData().spinnerList[5]
                    }
                    6 -> {
                        spinner2.adapter = ArrayAdapter(context ,android.R.layout.simple_spinner_item,
                            IngredientData().spinnerDairy)
                        sortName = IngredientData().spinnerList[6]
                    }
                    7 -> {
                        spinner2.adapter = ArrayAdapter(context ,android.R.layout.simple_spinner_item,
                            IngredientData().spinnerSeafood)
                        sortName = IngredientData().spinnerList[7]
                    }
                    8 -> {
                        spinner2.adapter = ArrayAdapter(context ,android.R.layout.simple_spinner_item,
                            IngredientData().spinnerSauce)
                        sortName = IngredientData().spinnerList[8]
                    }
                    9 -> {
                        spinner2.adapter = ArrayAdapter(context ,android.R.layout.simple_spinner_item,
                            IngredientData().spinnerSpice)
                        sortName = IngredientData().spinnerList[9]
                    }
                    10 -> {
                        spinner2.adapter = ArrayAdapter(context ,android.R.layout.simple_spinner_item,
                            IngredientData().spinnerHerb)
                        sortName = IngredientData().spinnerList[10]
                    }
                    11 -> {
                        spinner2.adapter = ArrayAdapter(context ,android.R.layout.simple_spinner_item,
                            IngredientData().spinnerCan)
                        sortName = IngredientData().spinnerList[11]
                    }
                    12 -> {
                        spinner2.adapter = ArrayAdapter(context ,android.R.layout.simple_spinner_item,
                            IngredientData().spinnerWine)
                        sortName = IngredientData().spinnerList[12]
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        val calendarDate = findViewById<TextView>(R.id.calendar_date)
        val calendar = findViewById<ImageButton>(R.id.calendar)
        val plusClear = findViewById<ImageButton>(R.id.plusclear)

        plusClear.setOnClickListener {
            Log.d("Plus"," "+ sortName+" "+date)
        }
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
                calendarDate.text = date
                mAlertDialog.dismiss()
            }
        }
    }

}