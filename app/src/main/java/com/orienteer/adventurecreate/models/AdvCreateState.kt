package com.orienteer.adventurecreate.models

import android.graphics.Bitmap
import com.airbnb.mvrx.MavericksState

data class AdvCreateState(
    val title: String = "",
    val description: String = "",
    val photos: List<Bitmap> = emptyList()
) : MavericksState