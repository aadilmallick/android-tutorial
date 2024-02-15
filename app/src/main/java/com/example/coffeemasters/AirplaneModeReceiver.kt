package com.example.coffeemasters

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log

class AirplaneModeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isAirplaneModeOn = Settings.Global.getInt(
                context?.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON
            ) != 0
            if (isAirplaneModeOn) {
                // Airplane mode is on
                Log.d("Receiver", "Airplane mode on")
            } else {
                // Airplane mode is off
                Log.d("Receiver", "Airplane mode off")
            }
        }
    }
}