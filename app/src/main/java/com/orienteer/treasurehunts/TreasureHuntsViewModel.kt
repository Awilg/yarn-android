package com.orienteer.treasurehunts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.orienteer.models.Adventure
import com.orienteer.models.ApiStatus
import com.orienteer.network.TreasureHuntApi
import com.orienteer.util.testHunts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class TreasureHuntsViewModel(location: LatLng) : ViewModel() {

    private val _treasureHunts = MutableLiveData<List<Adventure>>()
    val treasureHunts: LiveData<List<Adventure>>
        get() = _treasureHunts

    private val _navigateToSelectedTreasureHunt = MutableLiveData<Adventure>()
    val navigateToSelectedAdventure: LiveData<Adventure>
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

        // TODO: use location from constructor to get treasure hunts
        getTreasureHuntsNearby(location)
    }

    /**
     * Gets Mars real estate property information from the Mars API Retrofit service and updates the
     * [MarsProperty] [List] and [MarsApiStatus] [LiveData]. The Retrofit service returns a
     * coroutine Deferred, which we await to get the result of the transaction.
     */
    private fun getTreasureHuntsNearby(location: LatLng) {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            // TODO (04) Add filter to getProperties() with filter.value
            var getTreasureHunts = TreasureHuntApi.service.getTreasureHuntsByLocation(
                longitude = location.longitude.toString(), latitude = location.latitude.toString())
            try {
                _status.value = ApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val listResult = getTreasureHunts.await()
                _status.value = ApiStatus.DONE
                //_treasureHunts.value = listResult
            } catch (e: Exception) {
                //_status.value = ApiStatus.ERROR
                Timber.e("Network request failed with exception $e")
                _treasureHunts.value = testHunts
            }
        }
    }

    fun displayTreasureHuntDetails(adventure: Adventure) {
        _navigateToSelectedTreasureHunt.value = adventure
    }

    fun doneNavigatingToSelectedTreasureHunt(){
        _navigateToSelectedTreasureHunt.value = null
    }
}