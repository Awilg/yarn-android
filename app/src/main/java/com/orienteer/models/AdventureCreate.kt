package com.orienteer.models

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdventureCreate (
    var name: String,
    var location: LatLng,
    var description: String
) : Parcelable