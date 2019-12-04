package com.orienteer.map

import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.orienteer.models.Adventure
import com.orienteer.network.TreasureHuntApi
import com.orienteer.util.testHunts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

val DEFAULT_LOCATION = LatLng(-33.8523341, 151.2106085)
val DEFAULT_ZOOM = 15F

class MapViewModel : ViewModel() {
    val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

    private var _map: GoogleMap? = null

    private val _lastKnownLocation = MutableLiveData<Location>()
    val lastKnownLocation: LiveData<Location>
        get() = _lastKnownLocation

    private val _locationPermissionGranted = MutableLiveData<Boolean>()
    val locationPermissionGranted: LiveData<Boolean>
        get() = _locationPermissionGranted

    private val _isMyLocationButtonEnabled = MutableLiveData<Boolean>()
    val isMyLocationButtonEnabled: LiveData<Boolean>
        get() = _isMyLocationButtonEnabled

    private val _treasureHuntsNearby = MutableLiveData<List<Adventure>>()
    val treasureHuntsNearby: LiveData<List<Adventure>>
        get() = _treasureHuntsNearby

    private val _navigateToSelectedTreasureHunt = MutableLiveData<Adventure>()
    val navigateToSelectedAdventure: LiveData<Adventure>
        get() = _navigateToSelectedTreasureHunt

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


//    // The entry points to the Places API.
//    private val mGeoDataClient: GeoDataClient? = null
//    private val mPlaceDetectionClient: PlaceDetectionClient? = null

    // The entry point to the Fused Location Provider.
    private var _fusedLocationProviderClient: FusedLocationProviderClient? = null

    init {
        _locationPermissionGranted.value = false
        val defaultLoc = Location(LocationManager.GPS_PROVIDER)
        defaultLoc.latitude = DEFAULT_LOCATION.latitude
        defaultLoc.longitude = DEFAULT_LOCATION.longitude
        _lastKnownLocation.value = defaultLoc

        getTreasureHuntsNearby(LatLng(40.75,-74.0))
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

    fun moveMapToLocation(loc :LatLng) {
        _map?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                loc, DEFAULT_ZOOM
            )
        )
    }

    fun setMap(map: GoogleMap) {
        _map = map
        _map!!.setMinZoomPreference(6.0f)
        _map!!.setMaxZoomPreference(14.0f)

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
            Timber.e("Exception: %s", e.message!!)
        }

    }


    fun resetMap() {
        _map?.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM))
    }

    fun disableMyLocationButtonEnabled() {
        _isMyLocationButtonEnabled.value = false
    }

    /**
     * Gets Mars real estate property information from the Mars API Retrofit service and updates the
     * [MarsProperty] [List] and [MarsApiStatus] [LiveData]. The Retrofit service returns a
     * coroutine Deferred, which we await to get the result of the transaction.
     */
    // TODO (03) Add MarsApiFilter parameter to getMarsRealEstateProperties
    private fun getTreasureHuntsNearby(location: LatLng) {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            // TODO (04) Add filter to getProperties() with filter.value
            var getTreasureHunts = TreasureHuntApi.service.getTreasureHuntsByLocation(
                longitude = location.longitude.toString(), latitude = location.latitude.toString())
            try {
                // this will run on a thread managed by Retrofit
                val listResult = getTreasureHunts.await()
                //_treasureHuntsNearby.value = listResult
            } catch (e: Exception) {
                Timber.e("Network request failed with exception $e")
                _treasureHuntsNearby.value = testHunts
            }
        }
    }

    fun displayTreasureHuntDetails(adventure: Adventure) {
        _navigateToSelectedTreasureHunt.value = adventure
    }

    fun doneNavigatingToSelectedTreasureHunt(){
        _navigateToSelectedTreasureHunt.value = null
    }
}