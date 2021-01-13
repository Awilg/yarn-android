package com.orienteer.adventurecreate.models

import android.graphics.Bitmap
import com.airbnb.mvrx.MavericksState
import com.google.android.gms.maps.model.LatLng
import com.orienteer.models.BaseClue

data class AdvCreateState(
    val title: String? = null,
    val description: String? = null,
    val photos: List<Bitmap> = emptyList(),
    val clues: List<BaseClue> = emptyList(),
    val currentTextCluePrompt: String? = null,
    val currentTextClueAnswer: String? = null,
    val currentPhotoCluePrompt: String? = null,
    val currentPhotoCluePhotos: List<Bitmap> = emptyList(),
    val currentLocCluePrompt: String? = null,
    val currentLocClueLatLng: LatLng? = null,
    val currentFocusedSectionItem: SectionType = SectionType.TitleAndInfo,
    val isPrivateAdventure: Boolean? = null,
    val tipsEnabled: Boolean? = null,
    val treasureEnabled: Boolean? = null
) : MavericksState

data class SectionItem(
    val name: String,
    val type: SectionType,
    val completed: Boolean,
    val continuable: Boolean
)

enum class SectionType {
    TitleAndInfo,
    Photos,
    Publishing,
    Clues,
    TipsAndTreasure,
    Review
}

val initialSectionList = listOf(
    (SectionItem(
        "Title & Information",
        completed = false,
        type = SectionType.TitleAndInfo,
        continuable = true
    )),
    (SectionItem(
        "Photos",
        completed = false,
        type = SectionType.Photos,
        continuable = false
    )),
    (SectionItem(
        "Publishing",
        completed = false,
        type = SectionType.Publishing,
        continuable = false
    )),
    (SectionItem(
        "Clues",
        completed = false,
        type = SectionType.Clues,
        continuable = false
    )),

    (SectionItem(
        "Tips & Treasure",
        completed = false,
        continuable = false,
        type = SectionType.TipsAndTreasure
    )),

    (SectionItem(
        "Review",
        completed = false,
        continuable = false,
        type = SectionType.Review
    )),
)
