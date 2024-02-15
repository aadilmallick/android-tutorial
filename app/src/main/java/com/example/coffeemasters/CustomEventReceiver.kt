package com.example.coffeemasters

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class CustomEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "CUSTOM_EVENT") {
            // Do something
            Log.d("Receiver", "Custom event received")
            Toast.makeText(context, "Custom event received", Toast.LENGTH_LONG).show()
        }
    }
}