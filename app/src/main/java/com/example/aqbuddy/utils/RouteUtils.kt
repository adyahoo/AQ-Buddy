package com.example.aqbuddy.utils

import kotlin.math.*

private fun haversineDistance(
    lat1: Double, lon1: Double,
    lat2: Double, lon2: Double
): Double {
    val R = 6371000.0 // Radius bumi dalam meter
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)

    val a = sin(dLat / 2).pow(2.0) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLon / 2).pow(2.0)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return R * c // Jarak dalam meter
}

fun isWithinRadius(
    lat1: Double, lon1: Double,
    lat2: Double, lon2: Double,
    radiusMeters: Double = 100.0
): Boolean {
    val distance = haversineDistance(lat1, lon1, lat2, lon2)
    return distance <= radiusMeters
}
