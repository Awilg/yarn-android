package com.orienteer.explore.viewmodel

import com.airbnb.mvrx.MavericksViewModel
import com.orienteer.explore.model.AdventureSummary
import com.orienteer.explore.model.ExploreState

class ExploreViewModelV2(initialState: ExploreState) :
    MavericksViewModel<ExploreState>(initialState) {

    init {
        val testAdv = AdventureSummary(
            "id",
            "https://i.redd.it/d2ifvz972nc51.png",
            "Hunt The Kraken",
            "There are whispers of its whereabouts..."
        )
        setState {
            copy(
                featuredAdventure = testAdv,
                nearbyAdventures = listOf(
                    testAdv.copy(id = "one"),
                    testAdv.copy(id = "two", title = "SECOND ONE"),
                    testAdv.copy(id = "three", title = "THIRD")
                ),
                sponsoredAdventures = listOf(
                    testAdv.copy(id = "one"),
                    testAdv.copy(id = "two", title = "SECOND ONE"),
                    testAdv.copy(id = "three", title = "THIRD")
                )
            )
        }
    }

}