package com.example.aqbuddy.data.remote.dto

data class RouteResponse(
    val features: List<RouteData>
)

data class RouteData(
    val geometry: Geometry
)

data class Geometry(
    val coordinates: List<List<Double>>
)