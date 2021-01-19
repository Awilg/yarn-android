package com.orienteer.detail.viewmodel

import com.airbnb.mvrx.MavericksViewModel
import com.google.android.gms.maps.model.LatLng
import com.orienteer.detail.model.AdvDetailState
import com.orienteer.models.Adventure
import com.orienteer.models.Clue
import com.orienteer.models.ClueState
import com.orienteer.models.ClueType

class AdvDetailViewModel(initialState: AdvDetailState) :
    MavericksViewModel<AdvDetailState>(initialState) {

    fun loadAdventure(id: String) {
        setState {
            copy(
                adventure = Adventure(
                    "id_7",
                    "Hunt d'Elizabeth de Ville Beacon",
                    "This is a really description. I want to see what happens to the layout when the description is this long. Nanananananananananananananan BATMAN!",
                    LatLng(47.536, -122.300),
                    listOf(
                        Clue(
                            "clue_1",
                            "This is the first clue!",
                            "This is a hint",
                            ClueType.Location,
                            LatLng(47.539, -122.305),
                            ClueState.ACTIVE
                        ),
                        Clue(
                            "clue_2",
                            "This is the second clue",
                            "Photo hint",
                            ClueType.Photo,
                            LatLng(47.539, -122.305),
                            ClueState.LOCKED
                        ),
                        Clue(
                            "clue_3",
                            "This is the third clue",
                            "This is a hint",
                            ClueType.Text,
                            LatLng(47.539, -122.305),
                            ClueState.LOCKED
                        ),
                        Clue(
                            "clue_4",
                            "This is the fourth clue",
                            "This is a hint",
                            ClueType.Location,
                            LatLng(47.539, -122.305),
                            ClueState.LOCKED
                        ),
                        Clue(
                            "clue_5",
                            "This is the fifth clue",
                            "This is a hint",
                            ClueType.Location,
                            LatLng(47.539, -122.305),
                            ClueState.LOCKED
                        ),
                        Clue(
                            "clue_6",
                            "This is the sixth clue",
                            "This is a hint",
                            ClueType.Location,
                            LatLng(47.539, -122.305),
                            ClueState.LOCKED
                        ),
                        Clue(
                            "clue_7",
                            "This is the seventh clue",
                            "This is a hint",
                            ClueType.Location,
                            LatLng(47.539, -122.305),
                            ClueState.LOCKED
                        ),
                        Clue(
                            "clue_8",
                            "This is the eighth clue",
                            "This is a hint",
                            ClueType.Location,
                            LatLng(47.539, -122.305),
                            ClueState.LOCKED
                        ),
                        Clue(
                            "clue_9",
                            "This is the ninth clue",
                            "This is a hint",
                            ClueType.Location,
                            LatLng(47.539, -122.305),
                            ClueState.LOCKED
                        )
                    )
                )
            )
        }
    }
}