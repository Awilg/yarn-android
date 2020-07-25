package com.orienteer.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.orienteer.models.Adventure
import com.orienteer.util.testHunts

class ExploreViewModel : ViewModel() {

    private val _navigateToSelectedTreasureHunt = MutableLiveData<Adventure>()
    val navigateToSelectedAdventure: LiveData<Adventure>
        get() = _navigateToSelectedTreasureHunt

    fun displayTreasureHuntDetails(adventure: Adventure) {
        _navigateToSelectedTreasureHunt.value = adventure
    }

    fun doneNavigatingToSelectedTreasureHunt() {
        _navigateToSelectedTreasureHunt.value = null
    }

    private val _treasureHuntsNearby = MutableLiveData<List<Adventure>>()
    val treasureHuntsNearby: LiveData<List<Adventure>>
        get() = _treasureHuntsNearby

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
}