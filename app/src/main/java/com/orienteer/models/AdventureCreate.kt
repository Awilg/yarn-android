package com.orienteer.models

import com.google.android.gms.maps.model.LatLng

data class AdventureCreate (
    var name: String,
    var location: LatLng,
    var description: String,
    var clues: MutableList<BaseClue> = mutableListOf()
)