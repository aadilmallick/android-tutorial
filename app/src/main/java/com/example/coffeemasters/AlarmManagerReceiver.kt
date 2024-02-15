package com.example.coffeemasters

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmManagerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) {
            return
        }
        val itemData = intent.getSerializableExtra(AlarmManagerModel.EXTRA_KEY) as AlarmItem
        // TODO: perhaps create channel in application first
        NotificationsGlobalObject.messageNotificationsModel.createNotification(
            context.applicationContext,
            title = itemData.title,
            body = itemData.message
        )
    }
}