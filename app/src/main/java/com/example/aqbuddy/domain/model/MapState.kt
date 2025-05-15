package com.example.aqbuddy.domain.model

import android.graphics.drawable.Drawable
import com.utsman.osmandcompose.MarkerState

data class MarkerMapState(
    val state: MarkerState,
    val title: String? = null,
    val icon: Drawable? = null
)