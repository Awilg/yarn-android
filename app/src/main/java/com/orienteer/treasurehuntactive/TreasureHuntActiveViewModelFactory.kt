package com.orienteer.treasurehuntactive

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.orienteer.models.TreasureHunt
import com.orienteer.profile.ProfileViewModel

/**
 * Simple ViewModel factory that provides the MarsProperty and context to the ViewModel.
 */
class TreasureHuntActiveViewModelFactory(
    private val hunt: TreasureHunt,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return TreasureHuntActiveViewModel(hunt, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}