package com.orienteer.treasurehunts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.orienteer.models.AdventureInProgress
import com.orienteer.models.ApiStatus
import com.orienteer.models.TreasureHunt
import com.orienteer.network.TreasureHuntApi
import com.orienteer.util.TEST_ADVENTURES_IN_PROGRESS
import com.orienteer.util.TEST_HUNTS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class AdventuresFollowedViewModel : ViewModel() {

    private val _adventuresInProgress = MutableLiveData<List<AdventureInProgress>>()
    val adventuresInProgress: LiveData<List<AdventureInProgress>>
        get() = _adventuresInProgress

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {

        // TODO: use location from constructor to get treasure hunts
        getFollowedAdventures()
    }

    private fun getFollowedAdventures() {
        coroutineScope.launch {
            // TODO: call actual service
            _adventuresInProgress.value = TEST_ADVENTURES_IN_PROGRESS
        }
    }


}