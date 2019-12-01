package com.orienteer.treasurehuntdetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.orienteer.models.Adventure


/**
 * Simple [ViewModel] factory that provides the [Adventure] and context to the ViewModel.
 */
class TreasureHuntDetailViewModelFactory(
	private val adventure: Adventure,
	private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TreasureHuntDetailViewModel::class.java)) {
			return TreasureHuntDetailViewModel(adventure, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
