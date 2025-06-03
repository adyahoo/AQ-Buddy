package com.example.aqbuddy.domain.use_case

import android.util.Log
import com.example.aqbuddy.domain.repository.RouteServiceRepository
import com.example.aqbuddy.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.osmdroid.util.GeoPoint
import javax.inject.Inject

class RouteServiceUseCase @Inject constructor(
    private val repository: RouteServiceRepository
) {
    operator fun invoke(start: String, end: String): Flow<Resource<List<GeoPoint>>> {
        return flow {
            try {
                emit(Resource.Loading())

                val res = repository.getRoute(start, end).coordinates.map { GeoPoint(it[1], it[0]) }
                Log.d("masuk res", "$res")
                emit(Resource.Success(res))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown Error"))
            }
        }
    }
}