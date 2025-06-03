package com.example.aqbuddy.presentation.map

import androidx.compose.ui.graphics.Color
import com.example.aqbuddy.R
import com.utsman.osmandcompose.MarkerState
import org.osmdroid.util.GeoPoint

data class MapState(
    val markers: List<MarkerMapState> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val routes: List<GeoPoint>? = emptyList()
)

data class MarkerMapState(
    val state: MarkerState,
    val title: String? = null,
    val icon: Int? = null,
    val color: Color? = null,
    val score: Int = 0,
)

data class MapLegend(
    val title: String,
    val icon: Int,
) {
    companion object {
        fun getLegendData(): List<MapLegend> = listOf(
            MapLegend("Good", R.drawable.good_pin),
            MapLegend("Moderate", R.drawable.moderate_pin),
            MapLegend("Unhealthy Sensitive", R.drawable.unhealthy_sensitive_pin),
            MapLegend("Unhealthy", R.drawable.unhealthy_pin),
            MapLegend("Very Unhealthy", R.drawable.very_unhealthy_pin),
            MapLegend("Hazardous", R.drawable.hazardous_pin),
        )
    }
}