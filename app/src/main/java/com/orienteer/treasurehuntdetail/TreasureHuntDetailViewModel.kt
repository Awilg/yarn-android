package com.orienteer.treasurehuntdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orienteer.models.Adventure

class TreasureHuntDetailViewModel(adventure: Adventure, app: Application) : AndroidViewModel(app) {


	private val _selectedTreasureHunt = MutableLiveData<Adventure>()
	val selectedAdventure: LiveData<Adventure>
        get() = _selectedTreasureHunt

    // Initialize the _selectedProperty MutableLiveData
    init {
		_selectedTreasureHunt.value = adventure
    }
}
