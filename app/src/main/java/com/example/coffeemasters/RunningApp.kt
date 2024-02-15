package com.example.coffeemasters

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class RunningApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // need to create notification channel
        val channel = NotificationChannel(
            "foreground-service",
            "Foreground Service",
            NotificationManager.IMPORTANCE_HIGH
        )
//        val manager = getSystemService(NotificationManager::class.java) as NotificationManager

        // need to ask OS to create notification
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}