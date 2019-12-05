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
import com.orienteer.models.*
import com.orienteer.network.TreasureHuntApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class TreasureCreateViewModel : ViewModel() {

    private val _treasureHuntName = MutableLiveData<String>()
    val treasureHuntName: LiveData<String>
        get() = _treasureHuntName

    private val _treasureHuntLocation = MutableLiveData<Location>()
    val treasureHuntLocation: LiveData<Location>
        get() = _treasureHuntLocation

    private val _navigateToSuccessScreen = MutableLiveData<Boolean?>()
    val navigateToSuccessScreen: LiveData<Boolean?>
        get() = _navigateToSuccessScreen

    private val _navigateToPreview = MutableLiveData<Boolean?>()
    val navigateToPreview: LiveData<Boolean?>
        get() = _navigateToPreview

    private val _navigateAddClue = MutableLiveData<Boolean?>()
    val navigateAddClue: LiveData<Boolean?>
        get() = _navigateAddClue

    private val _currentAdventure = MutableLiveData<AdventureCreate>()
    val currentAdventure: LiveData<AdventureCreate>
        get() = _currentAdventure

    private val _clues = MutableLiveData<MutableList<BaseClue>?>()
    val clues: LiveData<MutableList<BaseClue>?>
        get() = _clues

    private var _map: GoogleMap? = null

    private val _lastKnownLocation = MutableLiveData<Location>()
    val lastKnownLocation: LiveData<Location>
        get() = _lastKnownLocation

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _currentAdventure.value = AdventureCreate(
            "Test adventure from Android",
            LatLng(47.6062, 122.3321),
            "This is a description!"
        )
    }

    fun doneNavigating() {
        _navigateToSuccessScreen.value = null
        _navigateToPreview.value = null
        _navigateAddClue.value = null
    }

    fun onCreateClicked() {
        _navigateToSuccessScreen.value = true
    }

    fun onPreviewClicked() {
        _navigateToPreview.value = true
    }

    fun onAddClueClicked() {
        _navigateAddClue.value = true
    }

    fun setTreasureHuntName(name: String) {
        _currentAdventure.value?.name = name
    }

    fun setTreasureHuntDescription(description: String) {
        _currentAdventure.value?.description = description
    }

    fun setTreasureHuntLocation(location: Location) {
        _treasureHuntLocation.value = location
    }

    fun addTextClue(clue: ClueTextCreate) {
        ensureClueListNonEmpty()
        _clues.value?.add(clue.toClue())
        // Observers only get called on a setValue()
        _clues.value = _clues.value
    }

    fun addLocationClue(clue: ClueLocation) {
        ensureClueListNonEmpty()
        _clues.value?.add(clue)
        // Observers only get called on a setValue()
        _clues.value = _clues.value
    }

    private fun ensureClueListNonEmpty() {
        if (_clues.value == null) {
            _clues.value = mutableListOf()
        }
    }

    fun updateClueLocationMap() {
        if (_lastKnownLocation.value == null) {
            resetClueLocationMap()
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

    fun setClueLocationLastKnownLocation(loc: Location?) {
        _lastKnownLocation.value = loc
        updateClueLocationMap()
    }

    fun setClueLocationMap(map: GoogleMap) {
        _map = map
        _map!!.setMinZoomPreference(4.0f)
        _map!!.setMaxZoomPreference(19.0f)
    }

    fun resetClueLocationMap() {
        _map?.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM))
    }

    fun getClueLocationCenterMap(): LatLng {
        return _map?.cameraPosition?.target!!
    }

    fun saveAdventure() {
        _currentAdventure.value?.let {
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                val saveAdventure = TreasureHuntApi.service.saveAdventure(it)
                try {
                    // this will run on a thread managed by Retrofit
                    val result = saveAdventure.await()
                    //_currentUser.value = result
                } catch (e: Exception) {
                    //_status.value = ApiStatus.ERROR
                    Timber.e("Network request failed with exception $e")
                    //_currentUser.value = TEST_USER
                }
            }
        }
    }

}