package com.orienteer.adventurecreate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orienteer.adventurecreate.controller.AdvCreateEpoxyListener

class AdvCreateViewModel :  ViewModel(), AdvCreateEpoxyListener {

    private val _navigateToCreateNewAdventure = MutableLiveData<Boolean>()
    val navigateToCreateNewAdventure: LiveData<Boolean>
        get() = _navigateToCreateNewAdventure

    init {

    }

    fun doneNavigateToCreateNewAdventure() {
        _navigateToCreateNewAdventure.value = false
    }

    override fun navigateToCreateNew() {
        _navigateToCreateNewAdventure.value = true
    }
}