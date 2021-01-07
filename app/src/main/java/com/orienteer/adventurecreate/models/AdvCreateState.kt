package com.orienteer.adventurecreate.models

import com.airbnb.mvrx.MavericksState

data class AdvCreateState(val title : String = "",
                          val description : String = "") : MavericksState