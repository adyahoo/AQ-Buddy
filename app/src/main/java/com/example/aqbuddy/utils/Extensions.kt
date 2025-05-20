package com.example.aqbuddy.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.provider.Settings
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.aqbuddy.R
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

fun Double.getAqiIcon(): Int {
    return when {
        this in 0.0..50.0 -> R.drawable.good_pin
        this in 51.0..100.0 -> R.drawable.moderate_pin
        this in 101.0..150.0 -> R.drawable.unhealthy_sensitive_pin
        this in 151.0..200.0 -> R.drawable.unhealthy_pin
        this in 201.0..300.0 -> R.drawable.very_unhealthy_pin
        this > 300.0 -> R.drawable.hazardous_pin
        else -> R.drawable.moderate_pin
    }
}

fun Context.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.gotoApplicationSettings() {
    startActivity(Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = "package:${packageName}".toUri()
    })
}

fun Context.hasLocationPermission() =
    PermissionUtils.locationPermissions.all {
        ContextCompat.checkSelfPermission(
            this,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }