package com.example.aqbuddy.domain.repository

import com.example.aqbuddy.data.remote.dto.Geometry

interface RouteServiceRepository {
    suspend fun getRoute(start: String, end: String): Geometry
}