package com.orienteer.adventurecreate.fragments

import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.createSectionHeader
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvCreateClueLocationFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()


    override fun epoxyController() = simpleController(viewModel) { state ->
        createSectionHeader {
            id("id_section_location_clue")
            header("Location Clue Editor")
            subtitle("Location clues consist of a clue prompt, a solution that is a physical location, and some optional hints.")
        }
    }
}