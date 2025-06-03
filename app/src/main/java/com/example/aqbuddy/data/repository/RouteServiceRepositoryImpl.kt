package com.example.aqbuddy.data.repository

import com.example.aqbuddy.data.remote.api.RouteServiceApi
import com.example.aqbuddy.data.remote.dto.Geometry
import com.example.aqbuddy.domain.repository.RouteServiceRepository
import javax.inject.Inject

class RouteServiceRepositoryImpl @Inject constructor(
    private val api: RouteServiceApi
) : RouteServiceRepository {
    override suspend fun getRoute(start: String, end: String): Geometry = api.getRoute(start, end).features[0].geometry
}