package com.orienteer.map

import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng


class MapViewModel : ViewModel() {
    private var _map: GoogleMap? = null

    private val _lastKnownLocation = MutableLiveData<Location>()
    val lastKnownLocation: LiveData<Location>
        get() = _lastKnownLocation

    private val _locationPermissionGranted = MutableLiveData<Boolean>()
    val locationPermissionGranted: LiveData<Boolean>
        get() = _locationPermissionGranted

    // Add observer to show button
    private val _isMyLocationButtonEnabled = MutableLiveData<Boolean>()
    val isMyLocationButtonEnabled: LiveData<Boolean>
        get() = _isMyLocationButtonEnabled

//    // The entry points to the Places API.
//    private val mGeoDataClient: GeoDataClient? = null
//    private val mPlaceDetectionClient: PlaceDetectionClient? = null

    // The entry point to the Fused Location Provider.
    private var _fusedLocationProviderClient: FusedLocationProviderClient? = null

    private val DEFAULT_LOCATION = LatLng(-33.8523341, 151.2106085)
    private val DEFAULT_ZOOM = 15F
    val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

    init {
        _locationPermissionGranted.value = false
        val defaultLoc = Location(LocationManager.GPS_PROVIDER)
        defaultLoc.latitude = DEFAULT_LOCATION.latitude
        defaultLoc.longitude = DEFAULT_LOCATION.longitude
        _lastKnownLocation.value = defaultLoc
    }

    fun setLocationPermissionGranted(granted: Boolean) {
        _locationPermissionGranted.value = granted
    }

    fun setLastKnownLocation(loc: Location?) {
        _lastKnownLocation.value = loc
        updateMap()
    }

    fun updateMap() {
        if (_lastKnownLocation.value == null) {
            resetMap()
        } else {
            _map?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        _lastKnownLocation.value!!.latitude,
                        _lastKnownLocation.value!!.longitude
                    ), DEFAULT_ZOOM
                )
            )
        }
    }

    fun setMap(map: GoogleMap) {
        _map = map
    }
    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    fun updateLocationUI() {
        if (_map == null) {
            return
        }
        try {
            if (_locationPermissionGranted.value!!) {
                _isMyLocationButtonEnabled.value = true
                _map?.isMyLocationEnabled = true
                _map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                _isMyLocationButtonEnabled.value =false
                _map?.isMyLocationEnabled = false
                _map?.uiSettings?.isMyLocationButtonEnabled = false
                _lastKnownLocation.value = null
                _locationPermissionGranted.value = false
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message)
        }

    }


    fun resetMap() {
        _map?.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM))
    }

    fun disableMyLocationButtonEnabled() {
        _isMyLocationButtonEnabled.value = false
    }
}