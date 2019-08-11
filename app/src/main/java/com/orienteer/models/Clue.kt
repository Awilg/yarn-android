package com.orienteer.models

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Clue(
    val id : String,
    val prompt : String,
    val hint : String,
    val type : ClueType,
    val location : LatLng
) : Parcelable
