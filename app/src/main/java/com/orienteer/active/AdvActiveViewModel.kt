package com.orienteer.active

import com.airbnb.mvrx.MavericksViewModel
import com.orienteer.models.Adventure

class AdvActiveViewModel(initialState: AdvActiveState) :
    MavericksViewModel<AdvActiveState>(initialState) {
    fun setAdventure(adventure: Adventure) {
        setState { copy(adventure = adventure) }
    }
}