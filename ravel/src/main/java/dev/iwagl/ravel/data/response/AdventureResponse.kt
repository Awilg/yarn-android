package dev.iwagl.ravel.data.response

import android.os.*
import kotlinx.android.parcel.*

@Parcelize
data class AdventureResponse(
    val id: String,
    val name: String
) : Parcelable