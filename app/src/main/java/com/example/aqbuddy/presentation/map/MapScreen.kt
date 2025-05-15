package com.example.aqbuddy.presentation.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.utsman.osmandcompose.DefaultMapProperties
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.MarkerState
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.ZoomButtonVisibility
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import com.utsman.osmandcompose.rememberOverlayManagerState
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.CopyrightOverlay
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@Composable
fun MapScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        // define camera state
        val cameraState = rememberCameraState {
            geoPoint = GeoPoint(
                -7.75819934849922,
                110.37242862161,
            )
            zoom = 20.0 // optional, default is 5.0
        }

        // map properties
        var mapProperties by remember {
            mutableStateOf(DefaultMapProperties)
        }

        SideEffect {
            mapProperties = mapProperties
                .copy(zoomButtonVisibility = ZoomButtonVisibility.ALWAYS)
        }

        // add node
        OpenStreetMap(
            modifier = Modifier.fillMaxSize(),
            cameraState = cameraState,
            properties = mapProperties,
            onFirstLoadListener = {
                viewModel.loadMarker()
            }
        ) {
            for (marker in viewModel.markers) {
                Marker(
                    state = marker.state,
                    title = marker.title
                )
            }
        }
    }
}