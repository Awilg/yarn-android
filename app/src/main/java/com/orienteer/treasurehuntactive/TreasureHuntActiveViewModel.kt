package com.orienteer.treasurehuntactive

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.orienteer.models.Clue
import com.orienteer.models.TreasureHunt
import timber.log.Timber

class TreasureHuntActiveViewModel (hunt: TreasureHunt, app: Application) : AndroidViewModel(app) {

    private val LOCATION_SOLVE_DISTANCE_METERS = 5f;
    private val _activeAdventure = MutableLiveData<TreasureHunt>()
    val activeAdventure: LiveData<TreasureHunt>
        get() = _activeAdventure

    private val _clues = MutableLiveData<List<Clue>>()
    val clues: LiveData<List<Clue>>
        get() = _clues

    private val _currentActiveClue = MutableLiveData<Clue>()
    val currentActiveClue: LiveData<Clue>
        get() = _currentActiveClue

    // Initialize the _selectedProperty MutableLiveData
    init {
        _activeAdventure.value = hunt
        _clues.value = hunt.clues
    }

    fun setCurrentClue(clue: Clue) {
        _currentActiveClue.value = clue
    }

    fun attemptLocationSolve(loc: Location) : Boolean {

        val results = FloatArray(1)
        _currentActiveClue.value?.let {
            Timber.i("Checking location $loc against current clue location ${it.location}...")
            Location.distanceBetween(it.location.latitude, it.location.longitude, loc.latitude, loc.longitude, results)

            if (results.first() < LOCATION_SOLVE_DISTANCE_METERS) {
                // Location is within given radius
                return true
            }
        }

        return false
    }
}