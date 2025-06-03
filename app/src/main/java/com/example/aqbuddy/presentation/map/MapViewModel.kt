package com.example.aqbuddy.presentation.map

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aqbuddy.domain.use_case.FBaseMapUseCase
import com.example.aqbuddy.domain.use_case.RouteServiceUseCase
import com.example.aqbuddy.utils.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.utsman.osmandcompose.CameraState
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
    private val routeServiceUseCase: RouteServiceUseCase,
    @ApplicationContext context: Context
) : ViewModel() {
    lateinit var cameraState: CameraState

    private var fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val _state = mutableStateOf(MapState())
    private val _curGeoPoint = mutableStateOf<GeoPoint?>(null)
    private val _clickedPoint = mutableStateOf<GeoPoint?>(null)

    val state: State<MapState> = _state
    val curGeoPoint: State<GeoPoint?> = _curGeoPoint
    val clickedPoint: State<GeoPoint?> = _clickedPoint

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
            null,
        )

        currentLoc.addOnSuccessListener {
            _curGeoPoint.value = GeoPoint(
                it.latitude,
                it.longitude
            )

            onSuccess()
        }
    }

    fun moveToCurrentLocation() {
        cameraState.animateTo(_curGeoPoint.value!!)
    }

    fun onMapClicked(point: GeoPoint) {
//        _clickedPoint.value = point

        val start = "${_curGeoPoint.value!!.longitude},${_curGeoPoint.value!!.latitude}"
        val end = "${point.longitude},${point.latitude}"

        routeServiceUseCase.invoke(start, end).onEach {
            when (it) {
                is Resource.Success -> {
//                    moveToCurrentLocation()
                    _state.value = _state.value.copy(
                        routes = it.data,
                        isLoading = false,
                        error = null
                    )

                    _clickedPoint.value = it.data?.last()
                }

                is Resource.Error -> {
                    Log.d("masuk error", "${it.error}")
                    _state.value = _state.value.copy(
                        error = it.error,
                        isLoading = false
                    )

                    _clickedPoint.value = point
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}