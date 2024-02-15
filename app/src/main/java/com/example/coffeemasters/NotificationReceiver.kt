package com.example.coffeemasters

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Notification received", Toast.LENGTH_SHORT).show()
        Log.d("NotificationReceiver", "Notification received")
        TODO("Not yet implemented")
    }
}