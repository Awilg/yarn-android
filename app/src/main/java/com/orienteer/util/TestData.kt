package com.orienteer.util

import com.google.android.gms.maps.model.LatLng
import com.orienteer.models.TreasureHunt
import com.orienteer.models.User
import java.util.*

// Seattle LatLng
var TEST_HUNTS : List<TreasureHunt> = listOf(
    TreasureHunt("id_1", "Treasure Hunt 1", LatLng(47.6062, 122.3321)),
    TreasureHunt("id_2", "Treasure Hunt 2", LatLng(47.82, 122.320)),
    TreasureHunt("id_3", "Treasure Hunt 3", LatLng(48.0, 122.370)),
    TreasureHunt("id_4", "Treasure Hunt 4", LatLng(46.9, 122.399)),
    TreasureHunt("id_5", "Treasure Hunt 5", LatLng(47.2062, 122.3412)),
    TreasureHunt("id_6", "Treasure Hunt 6", LatLng(47.309, 122.3662)),
    TreasureHunt("id_7", "Treasure Hunt 7", LatLng(47.536, 122.300)),
    TreasureHunt("id_8", "Treasure Hunt 8", LatLng(48.6062, 122.803)),
    TreasureHunt("id_9", "Treasure Hunt 9", LatLng(48.022, 122.555)),
    TreasureHunt("id_10", "Treasure Hunt 10", LatLng(48.11, 122.222)),
    TreasureHunt("id_11", "Treasure Hunt 11", LatLng(49.02, 122.3321)),
    TreasureHunt("id_12", "Treasure Hunt 12", LatLng(46.992, 122.444)),
    TreasureHunt("id_13", "Treasure Hunt 13", LatLng(46.682, 123.0)),
    TreasureHunt("id_14", "Treasure Hunt 14", LatLng(47.442, 121.999)),
    TreasureHunt("id_15", "James's Hunt #1", LatLng(47.552, 121.89)),
    TreasureHunt("id_16", "Treasure Hunt 16", LatLng(47.6242, 121.75)),
    TreasureHunt("id_17", "Treasure Hunt 17", LatLng(47.6102, 123.111)),
    TreasureHunt("id_18", "Treasure Hunt 18", LatLng(47.69, 122.89)),
    TreasureHunt("id_19", "Pog pog pog", LatLng(47.77, 122.82)),
    TreasureHunt("id_20", "Treasure Hunt 20", LatLng(47.721, 122.420))
)

var TEST_USER : User = User("id", "James G.", listOf("id_15", "id_19"), emptyList(), Calendar.getInstance().time)