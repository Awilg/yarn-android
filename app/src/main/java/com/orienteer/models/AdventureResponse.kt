package com.orienteer.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdventureResponse(
    val id: String,
    val name: String
) : Parcelable