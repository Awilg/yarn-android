package com.orienteer.treasurehuntactive

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.orienteer.models.Clue
import com.orienteer.models.ClueState
import com.orienteer.models.TreasureHunt
import timber.log.Timber

class TreasureHuntActiveViewModel(hunt: TreasureHunt, app: Application) : AndroidViewModel(app) {

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

    private val _currentCluePosition = MutableLiveData<Int>()
    val currentCluePosition: LiveData<Int>
        get() = _currentCluePosition

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
            it.state = ClueState.COMPLETED
            val nextPosition = _currentCluePosition.value!! + 1
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
        //TODO: Verify against the backend here then unlock next clue
        unlockNextClue()
    }
}