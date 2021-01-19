package com.orienteer.active.model

import com.airbnb.mvrx.MavericksState
import com.orienteer.models.Adventure

data class AdvActiveState(
    val adventure: Adventure? = null,
    val currentClueIndex: Int = 0
) : MavericksState