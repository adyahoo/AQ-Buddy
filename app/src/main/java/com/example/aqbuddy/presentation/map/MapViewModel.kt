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
    private val _curGeoPoint = mutableStateOf<GeoPoint?>(null)

    val state: State<MapState> = _state
    val curGeoPoint: State<GeoPoint?> = _curGeoPoint

    fun initMap() {
        getNearbyAqi()
        getCurrentLocation(
            onSuccess = {
                _state.value = _state.value.copy(
                    isLoading = false
                )
            }
        )
    }

    private fun getNearbyAqi() {
        mapUseCase.invoke().onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = MapState(
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
    fun getCurrentLocation(
        onSuccess: () -> Unit = {}
    ) {
        val accuracy = Priority.PRIORITY_HIGH_ACCURACY
        val currentLoc = fusedLocationProviderClient.getCurrentLocation(
            accuracy,
            CancellationTokenSource().token
        )

        currentLoc.addOnSuccessListener {
            _curGeoPoint.value = GeoPoint(
                it.latitude,
                it.longitude
            )

            onSuccess()
        }
    }
}