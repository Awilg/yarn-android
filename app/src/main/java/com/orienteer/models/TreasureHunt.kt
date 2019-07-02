package com.orienteer.models

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

/**
 * This data class defines a TreasureHunt which includes an ID, the location of the treasure, the name of the hunt,
 * the creator of the hunt, .
 * The property names of this data class are used by Moshi to match the names of values in JSON.
 */
@Parcelize
data class TreasureHunt(
    val id: String,
    val location: LatLng,
    val name: String) : Parcelable