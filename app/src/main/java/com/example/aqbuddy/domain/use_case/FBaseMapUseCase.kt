package com.example.aqbuddy.domain.use_case

import com.example.aqbuddy.domain.model.AqiData
import com.example.aqbuddy.domain.repository.FBaseMapRepository
import com.example.aqbuddy.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FBaseMapUseCase @Inject constructor(
    private val mapRepository: FBaseMapRepository
) {
    operator fun invoke(): Flow<Resource<List<AqiData>>> {
        return flow {
            try {
                emit(Resource.Loading())

                val aqiData = mutableListOf<AqiData>()
                val res = mapRepository.getNearbyAqi()

                res?.forEach { doc ->
                    val aqi = AqiData.from(doc.data)
                    aqiData.add(aqi)
                }

                emit(Resource.Success(aqiData))

            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }
}