package com.example.coffeemasters

import android.content.Context

class SharedPreferencesModel(private val context: Context) {
    public val sharedPreferences =
        context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
    public val editor = sharedPreferences.edit()
}