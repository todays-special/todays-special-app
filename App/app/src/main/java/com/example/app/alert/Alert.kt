package com.example.app.alert

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.app.R

class Alert(): Activity(){

    fun sendNotification(title: String = "", message: String = "", channelId: String= "") {

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
//        val notificationID = Random().nextInt(1000) // 0~999의 정수 랜덤 추출

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager, AlertSetting.CHANNEL_ID)
        }
        //야간설정
        if (title.isNotEmpty() && message.isNotEmpty()) {
            when(title){
                "야간알림설정" ->{
                    //            val intent = Intent(this, RefrigeratorStatus::class.java)
//            val pendingIntent = getActivity(this, 0, intent, FLAG_IMMUTABLE)

                    val notification = NotificationCompat.Builder(this, AlertSetting.CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
                        .build()
                    notificationManager.notify(AlertSetting.notificationID_night, notification)
                }
                "식재료소진알림설정" ->{
                    val notification = NotificationCompat.Builder(this, AlertSetting.CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
                        .build()
                    notificationManager.notify(AlertSetting.notificationID_NO_ITEM, notification)
                }
                "유통기한임박알림설정"->{
                    val notification = NotificationCompat.Builder(this, AlertSetting.CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
                        .build()
                    notificationManager.notify(AlertSetting.notificationID_EXP, notification)
                }
                "전체알림설정"->{
                    val notification = NotificationCompat.Builder(this, AlertSetting.CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
                        .build()
                    notificationManager.notify(AlertSetting.notificationID_ALL, notification)
                }
                else -> {
                    val notification = NotificationCompat.Builder(this, AlertSetting.CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
                        .build()
                    notificationManager.notify(AlertSetting.notificationID_ALL, notification)
                }
            }

        } else {

            val notification = NotificationCompat.Builder(this, AlertSetting.CHANNEL_ID)
                .setContentTitle("전체 알람 켜짐")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .build()
//            (System.currentTimeMillis()).toInt()
            // noti 쌓기
//            notificationManager.notify((System.currentTimeMillis()).toInt(), notification)

            notificationManager.notify(AlertSetting.notificationID_night, notification)
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
                AlertSetting.CHANNEL_NAME,
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