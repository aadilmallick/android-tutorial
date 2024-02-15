package com.example.coffeemasters



object NotificationsGlobalObject {
    private var NOTIFICATION_ID = 10
    val messageNotificationsModel =
        NotificationsModelV2(
            "alarm-manager-practice",
            getNotificationId(),
            "Alarm Manager Practice Notifications"
        )
    val repeatingNotificationsModel =
        NotificationsModelV2(
            "repeat-noti-practice",
            getNotificationId(),
            "Repeating Notifications Practice"
        )
    private fun getNotificationId(): Int {
        return NOTIFICATION_ID++
    }
}