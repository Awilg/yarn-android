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
import com.orienteer.models.BaseClue
import com.orienteer.models.ClueLocation
import com.orienteer.models.ClueTextCreate
import com.orienteer.models.toClue

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

    private val _clues = MutableLiveData<MutableList<BaseClue>?>()
    val clues: LiveData<MutableList<BaseClue>?>
        get() = _clues

    private var _map: GoogleMap? = null

    private val _lastKnownLocation = MutableLiveData<Location>()
    val lastKnownLocation: LiveData<Location>
        get() = _lastKnownLocation

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
        _treasureHuntName.value = name
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

}