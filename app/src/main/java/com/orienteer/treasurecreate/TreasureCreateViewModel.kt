package com.orienteer.treasurecreate

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

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

    fun doneNavigating() {
        _navigateToSuccessScreen.value = null
    }

    fun onCreateClicked() {
        _navigateToSuccessScreen.value = true
    }

    fun setTreasureHuntName(name: String) {
        _treasureHuntName.value = name
    }

    fun setTreasureHuntLocation(location: Location) {
        _treasureHuntLocation.value = location
    }
}