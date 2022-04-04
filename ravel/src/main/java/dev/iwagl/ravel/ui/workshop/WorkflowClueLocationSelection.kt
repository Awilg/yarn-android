package dev.iwagl.ravel.ui.workshop

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.viewinterop.*
import com.airbnb.mvrx.compose.*
import com.google.android.libraries.maps.*
import com.google.android.libraries.maps.model.*
import com.google.maps.android.ktx.*
import dev.iwagl.ravel.ui.common.*
import kotlinx.coroutines.*


@Composable
fun WorkflowClueLocationSelection(
    navToClueLocationSummary: () -> Unit
) {
    // This is by default scoped to the closest LifecycleOwner
    val viewModel: WorkflowViewModel = mavericksActivityViewModel()

    CityMapView("47.6062", "122.3321")

}


@Composable
private fun CityMapView(latitude: String, longitude: String) {
    // The MapView lifecycle is handled by this composable. As the MapView also needs to be updated
    // with input from Compose UI, those updates are encapsulated into the MapViewContainer
    // composable. In this way, when an update to the MapView happens, this composable won't
    // recompose and the MapView won't need to be recreated.
    val mapView = rememberMapViewWithLifecycle()
    MapViewContainer(mapView, latitude, longitude)
}

@Composable
private fun MapViewContainer(
    map: MapView,
    latitude: String,
    longitude: String
) {
    val cameraPosition = remember(latitude, longitude) {
        LatLng(latitude.toDouble(), longitude.toDouble())
    }

    LaunchedEffect(map) {
        val googleMap = map.awaitMap()
        googleMap.addMarker { position(cameraPosition) }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
    }

    var zoom by rememberSaveable(map) { mutableStateOf(InitialZoom) }

    val coroutineScope = rememberCoroutineScope()
    AndroidView({ map }) { mapView ->
        // Reading zoom so that AndroidView recomposes when it changes. The getMapAsync lambda
        // is stored for later, Compose doesn't recognize state reads
        val mapZoom = zoom
        coroutineScope.launch {
            val googleMap = mapView.awaitMap()
            googleMap.setZoom(mapZoom)
            // Move camera to the same place to trigger the zoom update
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
        }
    }
}

private const val InitialZoom = 5f
