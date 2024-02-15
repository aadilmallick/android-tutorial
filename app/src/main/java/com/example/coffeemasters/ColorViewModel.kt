package com.example.coffeemasters

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class ColorViewModel : ViewModel() {
    private var color = mutableStateOf(Color.Red)

    fun toggleColor() {
        color.value = if (color.value == Color.Red) Color.Blue else Color.Red
    }

    fun getColor() : Color {
        return color.value
    }

    fun getColorString() : String {
        return if (color.value == Color.Red) "Red" else "Blue"
    }
}