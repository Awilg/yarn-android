package com.orienteer.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.Date

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
    val startedAt: Date,
    val expiresAt: Date,
    val completed: Boolean) : Parcelable
