package com.orienteer.treasurehuntactive

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orienteer.models.Clue
import com.orienteer.models.TreasureHunt

class TreasureHuntActiveViewModel (hunt: TreasureHunt, app: Application) : AndroidViewModel(app) {

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
}