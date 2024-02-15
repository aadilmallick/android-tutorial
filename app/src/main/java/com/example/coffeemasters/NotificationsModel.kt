package com.example.coffeemasters

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class NotificationsModel(private val context: Context) {
    companion object {
        private var CHANNEL_ID = "notifications-practice"
        private var NOTIFICATION_ID = 1
        private var CHANNEL_NAME = "Notifications-Practice"
        private var requestCode = 1

        fun getRequestCode(): Int {
            return requestCode++
        }
    }


    private var channelIsCreated = false
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private fun sendBackToAppRightWay(): PendingIntent {
        // create an intent that takes you back to current class
        val intent = Intent(context, context.javaClass)
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                getRequestCode(),
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        return pendingIntent
    }

    private fun triggerToastReceiver() : PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            getRequestCode(),
            Intent(context, NotificationReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
    }



    fun createNotification(title: String, body: String) {
        if (!channelIsCreated) {
            createNotificationsChannel()
        }
        // build notification
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(sendBackToAppRightWay())
            .addAction(
                R.drawable.ic_launcher_foreground,
                "Go back",
                sendBackToAppRightWay()
            )
            .addAction(
                R.drawable.ic_launcher_foreground,
                "Send Toast",
                sendBackToAppRightWay()
            )
            .build()

        // send notification
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun createNotificationsChannel() {
        val channel = android.app.NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
        channelIsCreated = true
    }
}