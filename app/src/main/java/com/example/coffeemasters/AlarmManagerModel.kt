package com.example.coffeemasters

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.io.Serializable

data class AlarmItem(
    val title: String,
    val message: String,
) : Serializable

interface AlarmScheduler {
    fun schedule(alarmItem: AlarmItem)
    fun cancel(alarmItem: AlarmItem)
}

class AlarmManagerModel(private val context: Context)  {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private var item1 = AlarmItem("Alarm Title", "Alarm Message")

    fun setAlarmItem(alarmItem: AlarmItem) {
        item1 = alarmItem
    }

    private fun setPendingIntent(alarmItem: AlarmItem): PendingIntent {
        // triggers broadcast receiver, passes serializable extra
        return PendingIntent.getBroadcast(
            context,
            alarmItem.hashCode(),
            Intent(context, AlarmManagerReceiver::class.java).apply {
                this.putExtra(EXTRA_KEY, alarmItem)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun setRepeatingAlarm(timeInSeconds: Int) {
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            timeInSeconds.toLong() * 1000,
            setPendingIntent(item1)
        )
    }

    fun cancel() {
        alarmManager.cancel(setPendingIntent(item1))
    }

    companion object {
        val EXTRA_KEY = "${this::class.java}-item"
    }
}