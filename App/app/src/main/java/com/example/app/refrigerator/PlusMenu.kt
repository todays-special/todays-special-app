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
import com.example.app.plusminus.ControlData

class PlusMenu(context: Context) : Dialog(context) {

    var date: String = "null"
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
        var spinner3 = findViewById<Spinner>(R.id.spinner3)
        val volumeInput = findViewById<EditText>(R.id.volume_input)

        spinner3.adapter = ArrayAdapter.createFromResource(
            this.context,
            R.array.test2,
            android.R.layout.simple_spinner_item
        )

        spinner.adapter = ArrayAdapter(
            this.context, android.R.layout.simple_spinner_item,
            IngredientData().spinnerList
        )

        spinner.setSelection(0)
        sortName = IngredientData().spinnerList[0]
        spinner2.adapter = ArrayAdapter(
            context, android.R.layout.simple_spinner_item,
            IngredientData().spinnerGrain
        )
//        "쌀/곡식","견과","과일","채소","버섯","육류","유제품","해산물","소스","양념","허브","통조림","술"
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        //TODO spinner 아이템 가져오기
                        spinner2.adapter = ArrayAdapter(
                            context, android.R.layout.simple_spinner_item,
                            IngredientData().spinnerGrain
                        )
                    }
                    1 -> {
                        spinner2.adapter = ArrayAdapter(
                            context, android.R.layout.simple_spinner_item,
                            IngredientData().spinnerNut
                        )
                    }
                    2 -> {
                        spinner2.adapter = ArrayAdapter(
                            context, android.R.layout.simple_spinner_item,
                            IngredientData().spinnerFruit
                        )
                    }
                    3 -> {
                        spinner2.adapter = ArrayAdapter(
                            context, android.R.layout.simple_spinner_item,
                            IngredientData().spinnerVegetable
                        )
                    }
                    4 -> {
                        spinner2.adapter = ArrayAdapter(
                            context, android.R.layout.simple_spinner_item,
                            IngredientData().spinnerMushroom
                        )
                    }
                    5 -> {
                        spinner2.adapter = ArrayAdapter(
                            context, android.R.layout.simple_spinner_item,
                            IngredientData().spinnerMeat
                        )
                    }
                    6 -> {
                        spinner2.adapter = ArrayAdapter(
                            context, android.R.layout.simple_spinner_item,
                            IngredientData().spinnerDairy
                        )
                    }
                    7 -> {
                        spinner2.adapter = ArrayAdapter(
                            context, android.R.layout.simple_spinner_item,
                            IngredientData().spinnerSeafood
                        )
                    }
                    8 -> {
                        spinner2.adapter = ArrayAdapter(
                            context, android.R.layout.simple_spinner_item,
                            IngredientData().spinnerSauce
                        )
                    }
                    9 -> {
                        spinner2.adapter = ArrayAdapter(
                            context, android.R.layout.simple_spinner_item,
                            IngredientData().spinnerSpice
                        )
                    }
                    10 -> {
                        spinner2.adapter = ArrayAdapter(
                            context, android.R.layout.simple_spinner_item,
                            IngredientData().spinnerHerb
                        )
                    }
                    11 -> {
                        spinner2.adapter = ArrayAdapter(
                            context, android.R.layout.simple_spinner_item,
                            IngredientData().spinnerCan
                        )
                    }
                    12 -> {
                        spinner2.adapter = ArrayAdapter(
                            context, android.R.layout.simple_spinner_item,
                            IngredientData().spinnerWine
                        )
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
            //값을 내부 DB에 먼저 넣고, 서버에 동기화하기
            IngredientData().meat_beef
            val sortName = spinner.selectedItem.toString()
            val ingredient = spinner2.selectedItem.toString()
            val volume = volumeInput.text.toString()
            val volumeName = spinner3.selectedItem.toString()

            if (volume.isNotEmpty() && date != "null") {
                Log.d(
                    "Plus",
                    "$sortName $ingredient $volume $volumeName $date ${
                        IngredientData().getKeyName(ingredient)
                    }"
                )
                ControlData().insertData(
                    "test",
                    IngredientData().getKeyName(ingredient),
                    volume,
                    date
                )

                dismiss()
            } else {
                Log.d(
                    "NULL",
                    "$sortName $ingredient $volume $volumeName $date ${
                        IngredientData().getKeyName(ingredient)
                    }"
                )
                Toast.makeText(context, "입력이 올바르지않습니다", Toast.LENGTH_LONG).show()
            }
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