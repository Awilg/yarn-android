package com.orienteer.treasurehunts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.orienteer.models.ApiStatus
import com.orienteer.models.TreasureHunt
import com.orienteer.network.TreasureHuntApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TreasureHuntsViewModel : ViewModel() {

    private val _treasureHunts = MutableLiveData<List<TreasureHunt>>()
    val treasureHunts: LiveData<List<TreasureHunt>>
        get() = _treasureHunts

    private val _navigateToSelectedTreasureHunt = MutableLiveData<TreasureHunt>()
    val navigateToSelectedTreasureHunt: LiveData<TreasureHunt>
        get() = _navigateToSelectedTreasureHunt

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus>
        get() = _status

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getTreasureHunts()
    }

    /**
     * Gets Mars real estate property information from the Mars API Retrofit service and updates the
     * [MarsProperty] [List] and [MarsApiStatus] [LiveData]. The Retrofit service returns a
     * coroutine Deferred, which we await to get the result of the transaction.
     */
    // TODO (03) Add MarsApiFilter parameter to getMarsRealEstateProperties
    private fun getTreasureHunts() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            // TODO (04) Add filter to getProperties() with filter.value
            var getTreasureHunts = TreasureHuntApi.retrofitService.getTreasureHunts()
            try {
                _status.value = ApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val listResult = getTreasureHunts.await()
                _status.value = ApiStatus.DONE
                _treasureHunts.value = listResult
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                Log.e("TreasureHuntsViewModel", "Network request failed with exception $e")
                _treasureHunts.value = ArrayList()
            }
        }
    }

    fun displayTreasureHuntDetails(treasureHunt: TreasureHunt) {
        _navigateToSelectedTreasureHunt.value = treasureHunt
    }

    fun doneNavigatingToSelectedTreasureHunt(){
        _navigateToSelectedTreasureHunt.value = null
    }
}