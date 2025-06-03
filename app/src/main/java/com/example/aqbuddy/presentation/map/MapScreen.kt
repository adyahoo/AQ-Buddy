package com.example.aqbuddy.presentation.map

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MyLocation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.aqbuddy.R
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.MarkerState
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.Polygon
import com.utsman.osmandcompose.Polyline
import com.utsman.osmandcompose.rememberCameraState
import org.osmdroid.util.GeoPoint

@Composable
fun MapScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel()
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        // map
        RenderMap(navController)

        // back button
        RenderBackButton(
            navController = navController,
            modifier = Modifier.padding(16.dp)
        )

        // current location button
        RenderCurrentLocation(
            modifier = Modifier
                .padding(32.dp)
                .align(AbsoluteAlignment.BottomRight)
        )

        // map legend
        RenderLegend(
            modifier = Modifier
                .padding(
                    start = 32.dp,
                    bottom = 64.dp
                )
                .align(AbsoluteAlignment.BottomLeft)
        )

        // loading overlay
        if (viewModel.state.value.isLoading) {
            RenderLoadingOverlay()
        }
    }
}

@Composable
fun RenderMap(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var isMapLoaded by remember {
        mutableStateOf(false)
    }

    // define camera state
    viewModel.cameraState = rememberCameraState {
        geoPoint = viewModel.curGeoPoint.value ?: GeoPoint(0, 0)
        zoom = 15.0
    }

    LaunchedEffect(key1 = viewModel.curGeoPoint.value) {
        if (isMapLoaded && viewModel.curGeoPoint.value != null) {
            viewModel.cameraState.animateTo(viewModel.curGeoPoint.value!!)
        }
    }

    // map node
    OpenStreetMap(
        modifier = modifier.fillMaxSize(),
        cameraState = viewModel.cameraState,
        onFirstLoadListener = {
            viewModel.initMap()

            isMapLoaded = true
        },
        onMapClick = {
            viewModel.onMapClicked(it)
        },
    ) {
        if (viewModel.curGeoPoint.value != null) {
            Marker(
                id = "Current User Location",
                state = MarkerState(
                    geoPoint = viewModel.curGeoPoint.value!!
                ),
                icon = context.getDrawable(
                    R.drawable.twotone_person_pin_circle_24
                )
            )
        }

        if (viewModel.clickedPoint.value != null) {
            Marker(
                id = "Clicked Location",
                state = MarkerState(
                    geoPoint = viewModel.clickedPoint.value!!
                ),
                icon = context.getDrawable(
                    R.drawable.clicked_pin
                )
            )
        }

        if (viewModel.state.value.routes != null && viewModel.state.value.routes!!.isNotEmpty()) {
            Log.d("masuk routes", "${viewModel.state.value.routes}")
            Polyline(
                geoPoints = viewModel.state.value.routes!!
            )
        }

        for (marker in viewModel.state.value.markers) {
            Marker(
                id = marker.title,
                state = marker.state,
                icon = context.getDrawable(marker.icon!!)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(marker.color!!, RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = marker.score.toString(),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun RenderBackButton(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.White)
            .clickable {
                navController.popBackStack()
            }
            .padding(8.dp)
    ) {
        Icon(
            Icons.Rounded.ArrowBack,
            contentDescription = "Back Button",
            tint = Color.Black,
        )
    }
}

@Composable
fun RenderCurrentLocation(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel(),
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.White)
            .clickable {
                viewModel.moveToCurrentLocation()
            }
            .padding(8.dp)
    ) {
        Icon(
            Icons.Rounded.MyLocation,
            contentDescription = "Back Button",
            tint = Color.Black,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun RenderLegend(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
            .background(
                Color.White,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(8.dp)
    ) {
        MapLegend.getLegendData().forEach {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(it.icon),
                    contentDescription = it.title,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = it.title,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun RenderLoadingOverlay(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.5f))
    ) {
        CircularProgressIndicator(
            color = Color.White,
            strokeWidth = 2.dp,
            modifier = Modifier
                .align(Alignment.Center)
                .size(32.dp)
        )
    }
}