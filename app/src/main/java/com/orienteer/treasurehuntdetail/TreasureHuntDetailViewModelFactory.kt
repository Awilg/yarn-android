package com.orienteer.treasurehuntdetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.orienteer.models.TreasureHunt


/**
 * Simple [ViewModel] factory that provides the [TreasureHunt] and context to the ViewModel.
 */
class TreasureHuntDetailViewModelFactory(
    private val treasureHunt: TreasureHunt,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TreasureHuntDetailViewModel::class.java)) {
            return TreasureHuntDetailViewModel(treasureHunt, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}