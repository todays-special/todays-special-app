package com.example.app.alert

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH
import com.example.app.R
import com.example.app.Setting
import java.util.*
import kotlin.random.Random.Default.nextInt

class Alert : AppCompatActivity() {
    companion object Constants {
        const val CHANNEL_ID = "channel_id"
        const val CHANNEL_NAME = "channel_name"
        const val notificationID_ALL = 1000
        const val notificationID_NO_ITEM = 1001
        const val notificationID_NIGHT = 1002
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alter)

        val back= findViewById<ImageButton>(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }

        val switch1 = findViewById<SwitchCompat>(R.id.switch1)
        val switch2 = findViewById<SwitchCompat>(R.id.switch2)
        val switch3 = findViewById<SwitchCompat>(R.id.switch3)
        val switch4 = findViewById<SwitchCompat>(R.id.switch4)

        switch1.isChecked = true
        switch2.isChecked = true
        switch3.isChecked = true
        switch4.isChecked = true


        switch1.setOnClickListener{
            if (switch1.isChecked){
                switch2.isChecked = true
                switch3.isChecked = true
                switch4.isChecked = true
                // 알람이 켜졌습니다.
                sendNotification()
            }
            else {
                switch2.isChecked = false
                switch3.isChecked = false
                switch4.isChecked = false
            }
        }

        switch2.setOnClickListener{
            if(switch2.isChecked){

            }
            else{
                switch1.isChecked = false
            }
        }
        switch3.setOnClickListener{
            if(switch3.isChecked){

            }
            else{
                switch1.isChecked = false
            }
        }
        switch4.setOnClickListener{
            if(switch4.isChecked){

            }
            else{
                switch1.isChecked = false
            }
        }


    }

    private fun sendNotification(title: String = "", message: String = "") {

        val intent = Intent(this, Alert::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
//        val notificationID = Random().nextInt(1000) // 0~999의 정수 랜덤 추출

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val pendingIntent = getActivity(this, 0, intent, FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("전체 알람 켜짐")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationID_ALL, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
            description = "Channel Description"
            enableLights(true)
            lightColor = Color.GREEN
        }
        notificationManager.createNotificationChannel(channel)
    }
}