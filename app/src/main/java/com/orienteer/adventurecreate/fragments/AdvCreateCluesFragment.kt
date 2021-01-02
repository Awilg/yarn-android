package com.orienteer.adventurecreate.fragments

import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.createClueTypeSelection
import com.orienteer.createSectionHeader
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvCreateCluesFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()

    override fun epoxyController() = simpleController(viewModel) { state ->
        createSectionHeader {
            id(hashCode())
            header("Clues")
            subtitle("This is the meat and potatoes of your adventure. Like a fine 3 (or 20) course meal.")
        }

        createClueTypeSelection {
            id(hashCode())
        }
    }
}