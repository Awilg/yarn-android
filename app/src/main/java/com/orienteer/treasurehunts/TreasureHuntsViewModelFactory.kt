package com.orienteer.treasurehunts


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng


/**
 * Simple ViewModel factory that provides the MarsProperty and context to the ViewModel.
 */
class TreasureHuntsViewModelFactory(private val location: LatLng) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TreasureHuntsViewModel::class.java)) {
            return TreasureHuntsViewModel(location) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}