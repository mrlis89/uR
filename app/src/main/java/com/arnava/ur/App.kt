package com.arnava.ur

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

//        val notificationManager =
//            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val channel = NotificationChannel(
//            CHANNEL_ID,
//            CHANNEL_NAME,
//            NotificationManager.IMPORTANCE_HIGH
//        ).apply {
//            this.description = CHANNEL_DESC
//        }
//        channel.enableVibration(false)
//        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        lateinit var appContext: Context
    }
}