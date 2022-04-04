package dev.iwagl.ravel.data.response

import android.os.*
import com.squareup.moshi.*
import kotlinx.android.parcel.*
import java.time.*

/**
 * This data class defines an AdventureInProgress which includes information about the
 * relevant adventure, the progress of it, and the user.
 * The property names of this data class are used by Moshi to match the names of values in JSON.
 */
@Parcelize
data class AdventureInProgress(
    @Json(name = "_id")
    val id: String,
    val adventureId: String,
    val currentClueId: String,
    val startedAt: LocalDateTime,
    val expiresAt: LocalDateTime,
    val completed: Boolean) : Parcelable
