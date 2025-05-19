package com.example.aqbuddy.utils

import androidx.compose.ui.graphics.Color
import com.example.aqbuddy.ui.theme.aqiGoodColor
import com.example.aqbuddy.ui.theme.aqiHazardousColor
import com.example.aqbuddy.ui.theme.aqiModerateColor
import com.example.aqbuddy.ui.theme.aqiUnhealthyColor
import com.example.aqbuddy.ui.theme.aqiUnhealthySensitiveColor
import com.example.aqbuddy.ui.theme.aqiVeryUnhealthyColor

fun Double.getAqiColor(): Color {
    return when {
        this in 0.0..50.0 -> aqiGoodColor
        this in 51.0..100.0 -> aqiModerateColor
        this in 101.0..150.0 -> aqiUnhealthySensitiveColor
        this in 151.0..200.0 -> aqiUnhealthyColor
        this in 201.0..300.0 -> aqiVeryUnhealthyColor
        this > 300.0 -> aqiHazardousColor
        else -> aqiModerateColor
    }
}