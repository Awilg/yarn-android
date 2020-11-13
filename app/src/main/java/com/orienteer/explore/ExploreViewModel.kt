package com.orienteer.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.orienteer.models.Adventure
import com.orienteer.util.testFeaturedHunt
import com.orienteer.util.testHunts
import com.orienteer.util.testWfhHunts

class ExploreViewModel : ViewModel() {

    private val _featuredAdventure = MutableLiveData<Adventure>()
    val featuredAdventure: LiveData<Adventure>
        get() = _featuredAdventure

    private val _navigateToSelectedTreasureHunt = MutableLiveData<Adventure>()
    val navigateToSelectedAdventure: LiveData<Adventure>
        get() = _navigateToSelectedTreasureHunt

    private val _navigateToCallToCreate = MutableLiveData<Boolean>()
    val navigateToCallToCreate: LiveData<Boolean>
        get() = _navigateToCallToCreate

    fun displayTreasureHuntDetails(adventure: Adventure) {
        _navigateToSelectedTreasureHunt.value = adventure
    }

    fun doneNavigatingToSelectedTreasureHunt() {
        _navigateToSelectedTreasureHunt.value = null
    }

    fun navigateToCallToCreate() {
        _navigateToCallToCreate.value = true
    }

    fun doneNavigatingToCallToCreate() {
        _navigateToCallToCreate.value = false
    }

    private val _treasureHuntsNearby = MutableLiveData<List<Adventure>>()
    val treasureHuntsNearby: LiveData<List<Adventure>>
        get() = _treasureHuntsNearby

    private val _wfhAdventures = MutableLiveData<List<Adventure>>()
    val wfhAdventures: LiveData<List<Adventure>>
        get() = _wfhAdventures

    init {
        getTreasureHuntsNearby(LatLng(40.75,-74.0))
        getWfhAdventures()
        getFeaturedAdventure()
    }

    private fun getTreasureHuntsNearby(location: LatLng) {
        //coroutineScope.launch {
        // TODO - use actual location
//            var getTreasureHunts = TreasureHuntApi.service.getTreasureHuntsByLocation(
//                longitude = location.longitude.toString(), latitude = location.latitude.toString())
//            try {
//                // this will run on a thread managed by Retrofit
//                //val listResult = getTreasureHunts.await()
//                //_treasureHuntsNearby.value = listResult
//            } catch (e: Exception) {
//                Timber.e("Network request failed with exception $e")
        _treasureHuntsNearby.value = testHunts
        // }
        //}
    }

    private fun getFeaturedAdventure() {
        // TODO - use backend service
        _featuredAdventure.value = testFeaturedHunt
    }

    private fun getWfhAdventures() {
        // TODO - use backend service 2
        _wfhAdventures.value = testWfhHunts
    }
}