package com.orienteer.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.orienteer.models.User

/**
 * Simple ViewModel factory that provides the MarsProperty and context to the ViewModel.
 */
class TreasureHuntDetailViewModelFactory(
    private val user: User,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(user, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}