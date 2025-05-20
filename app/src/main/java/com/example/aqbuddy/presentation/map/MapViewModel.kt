package com.example.aqbuddy.presentation.map

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aqbuddy.domain.use_case.FBaseMapUseCase
import com.example.aqbuddy.utils.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.utsman.osmandcompose.MarkerState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.osmdroid.util.GeoPoint
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapUseCase: FBaseMapUseCase,
    @ApplicationContext context: Context
) : ViewModel() {
    private var fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val _state = mutableStateOf(MapState())
    private val _curLoc = mutableStateOf(
        GeoPoint(
            -7.75819934849922,
            110.37242862161,
        )
    )

    val state: State<MapState> = _state
    val currentGeoPoint: State<GeoPoint> = _curLoc

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
                                score = aqi.PM2_5.toInt(),
                                icon = aqi.icon,
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

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        val accuracy = Priority.PRIORITY_HIGH_ACCURACY
        val curLoc = fusedLocationProviderClient.getCurrentLocation(
            accuracy,
            CancellationTokenSource().token
        )

        _curLoc.value = GeoPoint(
            curLoc.result.latitude,
            curLoc.result.longitude
        )
    }
}