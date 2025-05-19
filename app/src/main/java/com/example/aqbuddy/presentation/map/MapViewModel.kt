package com.example.aqbuddy.presentation.map

import android.os.Handler
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aqbuddy.domain.use_case.FBaseMapUseCase
import com.example.aqbuddy.utils.Resource
import com.utsman.osmandcompose.MarkerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.osmdroid.util.GeoPoint
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapUseCase: FBaseMapUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(MapState())

    val state: State<MapState> = _state

    val currentGeoPoint = GeoPoint(
        -7.75819934849922,
        110.37242862161,
    )

    fun getNearbyAqi() {
        mapUseCase.invoke().onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = MapState(
                        isLoading = false,
                        markers = it.data!!.map { aqi ->
                            MarkerMapState(
                                state = MarkerState(
                                    aqi.location
                                ),
                                title = aqi.itemID,
                                color = aqi.color,
                                score = aqi.PM2_5.toInt()
                            )
                        }.toList()
                    )
                }

                is Resource.Error -> {
                    _state.value = MapState(
                        error = it.error,
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    _state.value = MapState(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getCurrentLocation() {

    }
}