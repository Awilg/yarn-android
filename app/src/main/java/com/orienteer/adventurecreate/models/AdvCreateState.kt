package com.orienteer.adventurecreate.models

import android.graphics.Bitmap
import com.airbnb.mvrx.MavericksState
import com.orienteer.models.BaseClue

data class AdvCreateState(
    val title: String = "",
    val description: String = "",
    val photos: List<Bitmap> = emptyList(),
    val clues: List<BaseClue> = emptyList(),
    val currentTextCluePrompt: String? = null,
    val currentTextClueAnswer: String? = null,
    val currentPhotoCluePrompt: String? = null,
    val currentPhotoCluePhotos: List<Bitmap> = emptyList()
) : MavericksState