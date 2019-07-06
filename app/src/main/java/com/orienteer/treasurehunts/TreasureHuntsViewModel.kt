package com.orienteer.treasurehunts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.orienteer.models.TreasureHunt

class TreasureHuntsViewModel : ViewModel() {

    private val _treasureHunts = MutableLiveData<List<TreasureHunt>>()
    val treasureHunts: LiveData<List<TreasureHunt>>
        get() = _treasureHunts

    private val _navigateToSelectedTreasureHunt = MutableLiveData<TreasureHunt>()
    val navigateToSelectedTreasureHunt: LiveData<TreasureHunt>
        get() = _navigateToSelectedTreasureHunt

    init {

        var testList = listOf(
            TreasureHunt(id = "test1", location = LatLng(0.0, 0.0), name = "Test Treasure Hunt 1"),
            TreasureHunt(id = "test2", location = LatLng(0.0, 0.0), name = "Boom boom boom"),
            TreasureHunt(id = "test3", location = LatLng(0.0, 0.0), name = "And anotha one"),
            TreasureHunt(id = "test4", location = LatLng(0.0, 0.0), name = "Liz was here!"),
            TreasureHunt(id = "test5", location = LatLng(0.0, 0.0), name = "android master race..."))

        _treasureHunts.value = testList
    }

    fun displayTreasureHuntDetails(treasureHunt: TreasureHunt) {
        _navigateToSelectedTreasureHunt.value = treasureHunt
    }

    fun doneNavigatingToSelectedTreasureHunt(){
        _navigateToSelectedTreasureHunt.value = null
    }
}