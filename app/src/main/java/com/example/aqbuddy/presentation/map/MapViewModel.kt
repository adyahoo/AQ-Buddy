package com.example.aqbuddy.presentation.map

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.aqbuddy.domain.model.MarkerMapState
import com.utsman.osmandcompose.MarkerState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.osmdroid.util.GeoPoint

class MapViewModel : ViewModel() {
    private val _markers = mutableStateListOf<MarkerMapState>()
    val markers: List<MarkerMapState> = _markers

    fun loadMarker() {
        val markers = listOf(
            MarkerMapState(
                state = MarkerState(
                    GeoPoint(
                        -7.758027660659193,
                        110.37394270499286
                    )
                ),
                title = "Marker1"
            ),
            MarkerMapState(
                state = MarkerState(
                    GeoPoint(
                        -7.75819934849922,
                        110.37242862161,
                    )
                ),
                title = "Marker2"
            ),
            MarkerMapState(
                state = MarkerState(
                    GeoPoint(
                        -7.756301,
                        110.374609
                    )
                ),
                title = "Marker3"
            ),
            MarkerMapState(
                state = MarkerState(
                    GeoPoint(
                        -7.756684,
                        110.372571
                    )
                ),
                title = "Marker4"
            ),
        )

        _markers.addAll(markers)
    }
}