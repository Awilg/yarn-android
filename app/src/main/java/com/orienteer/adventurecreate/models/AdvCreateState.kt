package com.orienteer.adventurecreate.models

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized

data class AdvCreateState(var inProgress: Async<AdvCreateInProgress> = Uninitialized) : MavericksState