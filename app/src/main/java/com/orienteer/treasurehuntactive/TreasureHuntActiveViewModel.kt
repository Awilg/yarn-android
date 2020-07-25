package com.orienteer.treasurehuntactive

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.orienteer.map.DEFAULT_LOCATION
import com.orienteer.map.DEFAULT_ZOOM
import com.orienteer.models.Adventure
import com.orienteer.models.Clue
import com.orienteer.models.ClueState
import com.orienteer.network.TreasureHuntApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class TreasureHuntActiveViewModel(hunt: Adventure, app: Application) : AndroidViewModel(app) {

    private val LOCATION_SOLVE_DISTANCE_METERS = 5f
    private val _activeAdventure = MutableLiveData<Adventure>()
    val activeAdventure: LiveData<Adventure>
        get() = _activeAdventure

    private val _clues = MutableLiveData<List<Clue>>()
    val clues: LiveData<List<Clue>>
        get() = _clues

    private val _currentActiveClue = MutableLiveData<Clue>()
    val currentActiveClue: LiveData<Clue>
        get() = _currentActiveClue

    private val _currentCluePosition = MutableLiveData<Int>()
    val currentCluePosition: LiveData<Int>
        get() = _currentCluePosition

    private val _navigateToCompletedScreen = MutableLiveData<Boolean?>()
    val navigateToCompletedScreen: LiveData<Boolean?>
        get() = _navigateToCompletedScreen


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


    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _activeAdventure.value = hunt
        _clues.value = hunt.clues
        if (_clues.value!!.first().state == ClueState.LOCKED) _clues.value!!.first().state = ClueState.ACTIVE
        _currentCluePosition.value = 0

        // TODO: get the person's progress from API
    }

    fun setCurrentClue(clue: Clue) {
        _currentActiveClue.value = clue
    }

    fun attemptLocationSolve(loc: Location): Boolean {
        val results = FloatArray(1)
        _currentActiveClue.value?.let {
            // DEBUG - just return true default for now
            unlockNextClue()
            return true

            Timber.i("Checking location $loc against current clue location ${it.location}...")
            Location.distanceBetween(it.location.latitude, it.location.longitude, loc.latitude, loc.longitude, results)

            if (results.first() < LOCATION_SOLVE_DISTANCE_METERS) {
                unlockNextClue()
                return true
            }
        }

        return false
    }

    private fun unlockNextClue() {
        _currentActiveClue.value?.let {
            // Check if we did all the clues
            val nextPosition = _currentCluePosition.value!! + 1
            if (_clues.value!!.lastIndex < nextPosition) {
                navigateToCompletedScreen()
                return
            }
            it.state = ClueState.COMPLETED
            _clues.value?.get(nextPosition)?.state = ClueState.ACTIVE
            _currentCluePosition.value = nextPosition
            _currentActiveClue.value = _clues.value?.get(nextPosition)
            // In order for the LiveData object to execute the OnChanged() method on the observer the setValue
            // method needs to be explicitly called.
            _clues.value = _clues.value
        }
    }

    fun attemptPhotoSolve() {}

    fun attemptTextSolve(answer: String) {

        _currentActiveClue.value?.let { clue ->
            coroutineScope.launch {

                val deferrableCheck = TreasureHuntApi.service.checkTextAnswer(
                    _activeAdventure.value?.id!!, clue.id,
                    answer)
                try {
                    // this will run on a thread managed by Retrofit
                    val result = deferrableCheck.await()
                    //_currentUser.value = result
                    if (result) {
                        Timber.i("TEXT CLUE SOLVED -- NAVIGATING TO SUCCESS SCREEN")
                        unlockNextClue()
                    }

                    Timber.i("TEXT CLUE FAILED")
                } catch (e: Exception) {
                    //_status.value = ApiStatus.ERROR
                    Timber.e("Network request failed with exception $e")
                    //_currentUser.value = TEST_USER
                }
            }
        }
    }

    fun navigateToCompletedScreen() {
        _navigateToCompletedScreen.value = true
    }

    fun doneNavigating() {
        _navigateToCompletedScreen.value = null
    }

    fun setMap(map: GoogleMap) {
        _map = map
        _map!!.setMinZoomPreference(6.0f)
        _map!!.setMaxZoomPreference(14.0f)

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

    fun resetMap() {
        _map?.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM))
    }

    fun disableMyLocationButtonEnabled() {
        _isMyLocationButtonEnabled.value = false
    }

    fun setLocationPermissionGranted(granted: Boolean) {
        _locationPermissionGranted.value = granted
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    fun updateLocationUI() {
        if (_map == null) {
            return
        }
        try {
            _map?.uiSettings?.isMapToolbarEnabled = false
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
}