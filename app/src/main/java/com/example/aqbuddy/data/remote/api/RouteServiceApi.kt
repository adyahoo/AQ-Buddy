package com.example.aqbuddy.data.remote.api

import com.example.aqbuddy.data.remote.dto.RouteResponse
import com.example.aqbuddy.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface RouteServiceApi {
    @GET("directions/foot-walking?api_key=${Constants.OPEN_ROUTE_SERVICE_API_KEY}")
    suspend fun getRoute(
        @Query("start") start: String,
        @Query("end") end: String
    ): RouteResponse
}