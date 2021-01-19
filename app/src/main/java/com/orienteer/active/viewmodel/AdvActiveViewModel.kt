package com.orienteer.active.viewmodel

import com.airbnb.mvrx.MavericksViewModel
import com.orienteer.active.model.AdvActiveState
import com.orienteer.models.Adventure

class AdvActiveViewModel(initialState: AdvActiveState) :
    MavericksViewModel<AdvActiveState>(initialState) {
    fun setAdventure(adventure: Adventure) {
        setState { copy(adventure = adventure) }
    }
}