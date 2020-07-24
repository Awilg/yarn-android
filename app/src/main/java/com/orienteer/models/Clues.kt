package com.orienteer.models

import android.net.Uri
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Clue(
    val id: String,
    val prompt: String,
    val hint: String,
    val type: ClueType,
    val location: LatLng,
    var state: ClueState,
    var textAnswer: String = ""
) : Parcelable

data class ClueTextCreate(
    val cluePrompt: String,
    val answer: String,
    val hint_1: String?,
    val hint_2: String?,
    val hint_3: String?
)

class ClueText(
    override var cluePrompt: String,
    override val type: String = ClueType.Text.name,
    override var hints: List<String>?  = emptyList(),
    val answer: String
) : BaseClue

class CluePhoto(
    override var cluePrompt: String,
    override val type: String = ClueType.Photo.name,
    override var hints: List<String>? = emptyList(),
    var imgUri: Uri = Uri.EMPTY,
    var tags: List<PhotoTag> = emptyList()
) : BaseClue

data class PhotoTag(
    val tag: String,
    val confidence: Double
)
class ClueLocation(
    override var cluePrompt: String,
    override val type: String = ClueType.Location.name,
    override var hints: List<String>?  = emptyList(),
    val location: LatLng
) : BaseClue

interface BaseClue {
    var cluePrompt: String
    val type: String
    var hints: List<String>?
}

enum class ClueType {
    Location,
    Photo,
    Text,
    Treasure
}

enum class ClueState {
    LOCKED,
    ACTIVE,
    COMPLETED
}

fun ClueTextCreate.toClue(): ClueText {
    return ClueText(
        cluePrompt = this.cluePrompt,
        answer = this.answer,
        hints = listOfNotNull(this.hint_1, this.hint_2, this.hint_3),
        type = ClueType.Text.name
    )
}