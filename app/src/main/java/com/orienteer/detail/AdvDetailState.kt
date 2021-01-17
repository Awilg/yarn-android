package com.orienteer.detail

import com.airbnb.mvrx.MavericksState
import com.orienteer.models.Adventure

data class AdvDetailState(val adventure: Adventure? = null) : MavericksState