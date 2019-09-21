package com.orienteer.treasurehunts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orienteer.models.AdventureInProgress
import com.orienteer.models.TreasureHunt

class AdventuresFollowedViewModel : ViewModel() {

    private val _adventuresInProgress = MutableLiveData<List<AdventureInProgress>>()
    val adventuresInProgress: LiveData<List<AdventureInProgress>>
        get() = _adventuresInProgress

    init {
        //TODO: call the service to retrieve these
    }
}