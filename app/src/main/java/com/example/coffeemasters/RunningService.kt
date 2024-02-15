package com.example.coffeemasters

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class RunningService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    // to start a service, we create an intent from an activity
    // then this method gets called when we do so
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            START_ACTION -> start()
            STOP_ACTION -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this,"foreground-service")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Running foreground service")
            .setContentText("notification body")
            .build()
        startForeground(1, notification)
    }

    companion object {
        val START_ACTION = "START"
        val STOP_ACTION = "STOP"
    }

}
