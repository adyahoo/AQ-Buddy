package com.example.aqbuddy.presentation.map

import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import com.utsman.osmandcompose.MarkerState

data class MapState(
    val markers: List<MarkerMapState> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

data class MarkerMapState(
    val state: MarkerState,
    val title: String? = null,
    val icon: Drawable? = null,
    val color: Color? = null,
    val score: Int = 0,
)
