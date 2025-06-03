package com.example.aqbuddy.utils

import org.osmdroid.util.GeoPoint
import kotlin.math.*

private val R = 6371000.0 // Radius bumi dalam meter

private fun haversineDistance(
    lat1: Double, lon1: Double,
    lat2: Double, lon2: Double
): Double {
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

fun generateCirclePolygon(centerLat: Double, centerLon: Double, radiusMeters: Double = 100.0, numPoints: Int = 40): List<GeoPoint> {
    val coords = mutableListOf<GeoPoint>()

    for (i in 0 until numPoints) {
        val angle = Math.toRadians(i * (360.0 / numPoints))
        val dx = radiusMeters * cos(angle)
        val dy = radiusMeters * sin(angle)

        val newLat = centerLat + (dy / R) * (180 / Math.PI)
        val newLon = centerLon + (dx / R) * (180 / Math.PI) / cos(Math.toRadians(centerLat))

        coords.add(GeoPoint(newLat, newLon))
    }

    return coords
}
