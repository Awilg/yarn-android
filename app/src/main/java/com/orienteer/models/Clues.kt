package com.orienteer.models

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
    override val cluePrompt: String,
    override val type: String = ClueType.Text.name,
    val answer: String,
    val hints: List<String>?
) : BaseClue(cluePrompt, type)


class ClueLocation(
    override val cluePrompt: String,
    override val type: String = ClueType.Location.name,
    val location: LatLng,
    val hints: List<String>? = null
) : BaseClue(cluePrompt, type)


open class BaseClue(
    open val cluePrompt: String,
    open val type: String
)

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
