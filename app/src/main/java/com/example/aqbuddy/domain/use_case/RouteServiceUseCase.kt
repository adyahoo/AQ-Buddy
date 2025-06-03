package com.example.aqbuddy.domain.use_case

import android.util.Log
import com.example.aqbuddy.domain.model.AqiData
import com.example.aqbuddy.domain.repository.FBaseMapRepository
import com.example.aqbuddy.domain.repository.RouteServiceRepository
import com.example.aqbuddy.utils.Resource
import com.example.aqbuddy.utils.isWithinRadius
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.osmdroid.util.GeoPoint
import javax.inject.Inject

class RouteServiceUseCase @Inject constructor(
    private val repository: RouteServiceRepository,
    private val mapRepository: FBaseMapRepository
) {
    private fun checkAqi(point: GeoPoint, aqiData: List<AqiData>): Boolean {
        for (aqi in aqiData) {
            val isWithinRadius = isWithinRadius(
                point.latitude, point.longitude,
                aqi.location.latitude, aqi.location.longitude
            )

            if (isWithinRadius) {
                return aqi.PM2_5 > 100
            }
        }

        return false
    }

    operator fun invoke(start: String, end: String): Flow<Resource<List<GeoPoint>>> {
        return flow {
            try {
                emit(Resource.Loading())

                val aqiData = mutableListOf<AqiData>()
                val res = repository.getRoute(start, end).coordinates.map { GeoPoint(it[1], it[0]) } as MutableList

                val aqiRes = mapRepository.getNearbyAqi()
                aqiRes?.forEach { doc ->
                    val aqi = AqiData.from(doc.data)
                    aqiData.add(aqi)
                }

                for (point in res) {
                    val isUnhealthy = checkAqi(point, aqiData)
                    if (isUnhealthy) {
                        val i = res.indexOf(point)
                        res[i] = GeoPoint(point.latitude + 0.001, point.longitude + 0.001)
                    }
                }

                Log.d("masuk res", "$res")
                emit(Resource.Success(res))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown Error"))
            }
        }
    }
}