package com.orienteer.treasurehuntactive

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.orienteer.models.Adventure

/**
 * Simple [ViewModel] factory that provides the [Adventure] and context to the ViewModel.
 */
class TreasureHuntActiveViewModelFactory(
    private val hunt: Adventure,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TreasureHuntActiveViewModel::class.java)) {
            return TreasureHuntActiveViewModel(hunt, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}