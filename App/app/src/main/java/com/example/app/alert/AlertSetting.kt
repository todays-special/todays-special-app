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
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.NotificationCompat
import com.example.app.R
import com.example.app.Setting
import java.util.*

class AlertSetting : AppCompatActivity() {
    companion object Constants {
        const val CHANNEL_ID = "channel_id"
        const val CHANNEL_NIGHT = "night"
        const val CHANNEL_NO = "no_item"
        const val CHANNEL_EXP = "no_exp"
        const val CHANNEL_ALL = "all"

        //이거로 생성된 채널이름을 확인해야됨. <- 이거때문에 에뮬별로 오류났음 os ver 10으로 고정
        const val CHANNEL_NAME = "channel_id"

        const val notificationID_ALL = 1000
        const val notificationID_NO_ITEM = 1001
        const val notificationID_night = 1000001
        const val notificationID_EXP = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alter)

        val back = findViewById<ImageButton>(R.id.back)
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


        switch1.setOnClickListener {
            if (switch1.isChecked) {
                switch2.isChecked = true
                switch3.isChecked = true
                switch4.isChecked = true
                // 알람이 켜졌습니다.
                sendNotification("전체알림설정", "전체알림이 켜졌습니다", CHANNEL_ALL)
            } else {
                switch2.isChecked = false
                switch3.isChecked = false
                switch4.isChecked = false
                sendNotification("전체알림설정", "전체알림이 꺼졌습니다", CHANNEL_ALL)
            }
        }

        switch2.setOnClickListener {
            if (switch2.isChecked) {
                sendNotification("유통기한임박알림설정", "설정이 켜졌습니다", CHANNEL_EXP)

            } else {
                switch1.isChecked = false
                sendNotification("유통기한임박알림설정", "설정이 꺼졌습니다", CHANNEL_EXP)
            }
        }
        switch3.setOnClickListener {
            if (switch3.isChecked) {
                sendNotification("식재료소진알림설정", "설정이 켜졌습니다", CHANNEL_NO)

            } else {
                switch1.isChecked = false
                sendNotification("식재료소진알림설정", "설정이 꺼졌습니다", CHANNEL_NO)

            }
        }
        switch4.setOnClickListener {
            if (switch4.isChecked) {
                sendNotification("야간알림설정", "설정이 켜졌습니다", CHANNEL_NIGHT)
            } else {
                switch1.isChecked = false
                sendNotification("야간알림설정", "설정이 꺼졌습니다", CHANNEL_NIGHT)
            }
        }
    }

    fun sendNotification(title: String = "", message: String = "", channelId: String= "") {

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
//        val notificationID = Random().nextInt(1000) // 0~999의 정수 랜덤 추출

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager, CHANNEL_ID)
        }
        //야간설정
        if (title.isNotEmpty() && message.isNotEmpty()) {
            when(title){
                "야간알림설정" ->{
                    //            val intent = Intent(this, RefrigeratorStatus::class.java)
//            val pendingIntent = getActivity(this, 0, intent, FLAG_IMMUTABLE)

                    val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
                        .build()
                    notificationManager.notify(notificationID_night, notification)
                }
                "식재료소진알림설정" ->{
                    val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
                        .build()
                    notificationManager.notify(notificationID_NO_ITEM, notification)
                }
                "유통기한임박알림설정"->{
                    val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
                        .build()
                    notificationManager.notify(notificationID_EXP, notification)
                }
                "전체알림설정"->{
                    val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
                        .build()
                    notificationManager.notify(notificationID_ALL, notification)
                }
            }


        } else {

            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("전체 알람 켜짐")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .build()
//            (System.currentTimeMillis()).toInt()
            // noti 쌓기
//            notificationManager.notify((System.currentTimeMillis()).toInt(), notification)

            notificationManager.notify(notificationID_night, notification)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        notificationManager: NotificationManager,
        channelId: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                channelId,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel Description"
                enableLights(true)
                lightColor = Color.GREEN
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}