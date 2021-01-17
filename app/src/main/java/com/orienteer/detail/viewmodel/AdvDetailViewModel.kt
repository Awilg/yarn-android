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

    fun getAdventure(id: String) {
        setState {
            copy(
                adventure = Adventure(
                    "kraken",
                    "Hunt The Kraken",
                    "There are whispers of its whereabouts...",
                    LatLng(47.6062, -122.3321),
                    listOf(
                        Clue(
                            "clue_1",
                            "This is a text clue",
                            "This is a hint",
                            ClueType.Text,
                            LatLng(47.539, -122.305),
                            ClueState.ACTIVE,
                            "answer"
                        )
                    ),
                    "https://i.redd.it/d2ifvz972nc51.png",
                    listOf(
                        "https://i.redd.it/d2ifvz972nc51.png",
                        "https://i0.wp.com/russianmachineneverbreaks.com/wp-content/uploads/2020/07/seattle-kraken-jerseys.png",
                        "https://www.terracestandard.com/wp-content/uploads/2020/07/22215638_web1_seattle-kraken.jpg"
                    )
                )
            )
        }
    }
}