package com.orienteer.treasurehuntactive

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orienteer.models.TreasureHunt

class TreasureHuntActiveViewModel (hunt: TreasureHunt, app: Application) : AndroidViewModel(app) {

    private val _activeAdventure = MutableLiveData<TreasureHunt>()
    val activeAdventure: LiveData<TreasureHunt>
        get() = _activeAdventure

    // Initialize the _selectedProperty MutableLiveData
    init {
        _activeAdventure.value = hunt
    }
}