package dev.iwagl.ravel.data.request

import com.google.android.gms.maps.model.*
import dev.iwagl.ravel.data.models.*

data class AdventureCreate (
    var name: String,
    var location: LatLng,
    var description: String,
    var clues: MutableList<BaseClue> = mutableListOf()
)