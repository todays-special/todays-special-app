package com.example.app


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class hello : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        val btn1 = findViewById<Button>(R.id.btn1)
        val next_introduce = findViewById<ImageButton>(R.id.next_introduce)
        val next_pan = findViewById<ImageButton>(R.id.next_pan)
        val next_main = findViewById<ImageButton>(R.id.next_main)
        val intro = findViewById<ConstraintLayout>(R.id.intro)
        val introduce = findViewById<ConstraintLayout>(R.id.introduce)
        val pan_setting = findViewById<ConstraintLayout>(R.id.pan_setting)

        next_introduce.setOnClickListener {
            intro.visibility = View.INVISIBLE
            introduce.visibility = View.VISIBLE
        }

        next_pan.setOnClickListener {
            intro.visibility = View.INVISIBLE
            introduce.visibility = View.INVISIBLE
            pan_setting.visibility = View.VISIBLE
        }

        next_main.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        btn1.setOnClickListener {
            showDialog()
        }
    }
    private  fun showDialog(){

        val mSelectedItem: ArrayList<String> = arrayListOf()

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setTitle("가진 조리도구를 선택하세요")

        builder.setMultiChoiceItems(R.array.pan, null){
            p0, which, isChecked ->

            val pan: Array<String> = resources.getStringArray(R.array.pan)

            if(isChecked){
                mSelectedItem.add(pan[which])
            }else{
                mSelectedItem.remove(pan[which])
            }
        }
        builder.setPositiveButton("ok"){
            p0, p1 ->
            var finalSelection = ""

            for(item: String in mSelectedItem){
            finalSelection =finalSelection + "\n" + item
        }
            Toast.makeText(this@hello, "선택 조리기구는 ${finalSelection}",
            Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Cancel"){
            dialog, p1 ->dialog.cancel()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

}