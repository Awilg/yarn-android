package com.orienteer.adventurecreate.models

import com.airbnb.mvrx.MavericksState

data class AdvCreateState(val title : String = "initial_state",
                          val description : String = "initial_state") : MavericksState