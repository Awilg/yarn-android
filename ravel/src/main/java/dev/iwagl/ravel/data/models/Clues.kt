package dev.iwagl.ravel.data.models

import android.net.*

data class ClueTextCreate(
    val cluePrompt: String,
    val answer: String,
    val hint_1: String?,
    val hint_2: String?,
    val hint_3: String?
)

data class ClueText(
    override var cluePrompt: String,
    override val type: String = ClueType.TEXT.name,
    override var hints: List<String>? = emptyList(),
    val answer: String
) : BaseClue

data class CluePhoto(
    override var cluePrompt: String,
    override val type: String = ClueType.PHOTO.name,
    override var hints: List<String>? = emptyList(),
    var imgUris: List<Uri> = emptyList(),
    var tags: List<PhotoTag> = emptyList()
) : BaseClue

data class PhotoTag(
    val tag: String,
    val confidence: Double
)

data class ClueLocation(
    override var cluePrompt: String,
    override val type: String = ClueType.LOCATION.name,
    override var hints: List<String>? = emptyList(),
//    val location: LatLng
) : BaseClue

interface BaseClue {
    var cluePrompt: String
    val type: String
    var hints: List<String>?
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
        type = ClueType.TEXT.name
    )
}