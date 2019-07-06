package com.orienteer.treasurehuntdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orienteer.models.TreasureHunt

class TreasureHuntDetailViewModel (treasureHunt: TreasureHunt, app: Application) : AndroidViewModel(app) {


    private val _selectedTreasureHunt = MutableLiveData<TreasureHunt>()
    val selectedTreasureHunt: LiveData<TreasureHunt>
        get() = _selectedTreasureHunt

    // Initialize the _selectedProperty MutableLiveData
    init {
        _selectedTreasureHunt.value = treasureHunt
    }
}