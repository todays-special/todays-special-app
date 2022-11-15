package com.example.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.app.alert.AlertSetting
import com.example.app.refrigerator.RefrigeratorStatus
import kotlinx.android.synthetic.main.activity_recipe_rec.*

class RecipeRec : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_rec)

        val home = findViewById<ImageButton>(R.id.home)
        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val refrigerator = findViewById<ImageButton>(R.id.refrigerator)
        refrigerator.setOnClickListener {
            val intent = Intent(this, RefrigeratorStatus::class.java)
            startActivity(intent)
        }
        button.setOnClickListener {
//           startActivity(Intent(this, UpdateTestActivity::class.java))

            //앞에 ~에 재료 stringbuilder로 묶어서 출력하면 될것같음
            val foodList = "~"
            sendNotification("재료차감", "${foodList}가 차감되었습니다")
        }
    }

    fun sendNotification(title: String = "", message: String = "", channelId: String = "") {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager, channelId)
        }
        //야간설정
        when (title) {
            "재료차감" -> {
                val intent = Intent(this, RefrigeratorStatus::class.java)
                val pendingIntent = getActivity(this, 0, intent, FLAG_IMMUTABLE)

                val notification = NotificationCompat.Builder(this, AlertSetting.CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build()
                notificationManager.notify((System.currentTimeMillis()).toInt(), notification)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        notificationManager: NotificationManager,
        channelId: String
    ) {
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


    //test중이라 주석처리해둠
//    fun onDialogClicked2(view: View){
//        val check = Check(this)
//        check.show()
//    }
}