package com.orienteer.treasurecreate

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.orienteer.map.DEFAULT_LOCATION
import com.orienteer.map.DEFAULT_ZOOM

class ClueTypeLocationViewModel : ViewModel() {
    private var _map: GoogleMap? = null

    private val _lastKnownLocation = MutableLiveData<Location>()
    val lastKnownLocation: LiveData<Location>
        get() = _lastKnownLocation

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

    fun setLastKnownLocation(loc: Location?) {
        _lastKnownLocation.value = loc
        updateMap()
    }

    fun setMap(map: GoogleMap) {
        _map = map
        _map!!.setMinZoomPreference(4.0f)
        _map!!.setMaxZoomPreference(19.0f)
    }
    fun resetMap() {
        _map?.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM))
    }
}