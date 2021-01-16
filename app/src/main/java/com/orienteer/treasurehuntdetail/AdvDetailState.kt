package com.orienteer.treasurehuntdetail

import com.airbnb.mvrx.MavericksState
import com.orienteer.models.Adventure

data class AdvDetailState(val adv: Adventure? = null) : MavericksState
