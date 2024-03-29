package com.orienteer.models

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * This data class defines a TreasureHunt which includes an ID, the location of the treasure, the name of the hunt,
 * the creator of the hunt, .
 * The property names of this data class are used by Moshi to match the names of values in JSON.
 */
@Parcelize
data class Adventure(
    @Json(name = "_id")
    val id: String,
    val name: String,
    val description: String,
    val location: LatLng,
    val clues: List<Clue>,
    val featuredImage: String = "",
    val featuredImageList: List<String> = emptyList()
) : Parcelable