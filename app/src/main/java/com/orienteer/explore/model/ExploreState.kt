package com.orienteer.explore.model

import com.airbnb.mvrx.MavericksState

data class ExploreState(
    val featuredAdventure: AdventureSummary? = null,
    val nearbyAdventures: List<AdventureSummary>? = null,
    val sponsoredAdventures: List<AdventureSummary>? = null
) : MavericksState

data class AdventureSummary(
    val id: String? = null,
    val featuredImage: String? = null,
    val title: String? = null,
    val description: String? = null,

    )