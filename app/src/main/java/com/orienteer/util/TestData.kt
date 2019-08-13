package com.orienteer.util

import com.google.android.gms.maps.model.LatLng
import com.orienteer.models.Clue
import com.orienteer.models.ClueType
import com.orienteer.models.TreasureHunt
import com.orienteer.models.User
import java.util.Calendar

// Seattle LatLng
var TEST_HUNTS : List<TreasureHunt> = listOf(
    TreasureHunt("id_1", "Treasure Hunt 1", LatLng(47.6062, -122.3321), listOf(
        Clue("clue_1", "This is the first clue", "This is a hint", ClueType.Location, LatLng(47.539, -122.305)),
        Clue("clue_2", "This is the photo clue", "Photo hint", ClueType.Photo, LatLng(47.539, -122.305)),
        Clue("clue_3", "This is the text clue", "This is a hint", ClueType.Text, LatLng(47.539, -122.305)))),
    TreasureHunt("id_2", "Treasure Hunt 2", LatLng(47.82, -122.320), emptyList()),
    TreasureHunt("id_3", "Treasure Hunt 3", LatLng(48.0, -122.370), emptyList()),
    TreasureHunt("id_4", "Treasure Hunt 4", LatLng(46.9, -122.399), emptyList()),
    TreasureHunt("id_5", "Treasure Hunt 5", LatLng(47.2062, -122.3412), emptyList()),
    TreasureHunt("id_6", "Treasure Hunt 6", LatLng(47.309, -122.3662), emptyList()),
    TreasureHunt("id_7", "Hunt d'Elizabeth", LatLng(47.536, -122.300), listOf(
        Clue("clue_1", "This is the first clue!", "This is a hint", ClueType.Location, LatLng(47.539, -122.305)),
        Clue("clue_2", "This is the second clue", "Photo hint", ClueType.Photo, LatLng(47.539, -122.305)),
        Clue("clue_3", "This is the third clue", "This is a hint", ClueType.Text, LatLng(47.539, -122.305)),
        Clue("clue_4", "This is the fourth clue", "This is a hint", ClueType.Location, LatLng(47.539, -122.305)),
        Clue("clue_5", "This is the fifth clue", "This is a hint", ClueType.Location, LatLng(47.539, -122.305)),
        Clue("clue_6", "This is the sixth clue", "This is a hint", ClueType.Location, LatLng(47.539, -122.305)),
        Clue("clue_7", "This is the seventh clue", "This is a hint", ClueType.Location, LatLng(47.539, -122.305)),
        Clue("clue_8", "This is the eighth clue", "This is a hint", ClueType.Location, LatLng(47.539, -122.305)),
        Clue("clue_9", "This is the ninth clue", "This is a hint", ClueType.Location, LatLng(47.539, -122.305)))),
    TreasureHunt("id_8", "Treasure Hunt 8", LatLng(48.6062, -122.803), emptyList()),
    TreasureHunt("id_9", "Treasure Hunt 9", LatLng(48.022, -122.555), emptyList()),
    TreasureHunt("id_10", "Treasure Hunt 10", LatLng(48.11, -122.222), emptyList()),
    TreasureHunt("id_11", "Treasure Hunt 11", LatLng(49.02, -122.3321), emptyList()),
    TreasureHunt("id_12", "Treasure Hunt 12", LatLng(46.992, -122.444), emptyList()),
    TreasureHunt("id_13", "Treasure Hunt 13", LatLng(46.682, -123.0), emptyList()),
    TreasureHunt("id_14", "Treasure Hunt 14", LatLng(47.442, -121.999), emptyList()),
    TreasureHunt("id_15", "James's Hunt #1", LatLng(47.552, -121.89), emptyList()),
    TreasureHunt("id_16", "Treasure Hunt 16", LatLng(47.6242, -121.75), emptyList()),
    TreasureHunt("id_17", "Treasure Hunt 17", LatLng(47.6102, -123.111), emptyList()),
    TreasureHunt("id_18", "Treasure Hunt 18", LatLng(47.69, -122.89), emptyList()),
    TreasureHunt("id_19", "Pog pog pog", LatLng(47.77, -122.82), emptyList()),
    TreasureHunt("id_20", "Treasure Hunt 20", LatLng(47.721, -122.420), emptyList())
)

var TEST_USER : User = User("id", "Liz", listOf("id_15", "id_19"), emptyList(), Calendar.getInstance().time)