package com.orienteer.adventurecreate.fragments

import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.createSectionHeader
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvCreatePublishingFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()

    override fun epoxyController() = simpleController(viewModel) { state ->
        createSectionHeader {
            id(hashCode())
            header("Publishing")
            subtitle("All adventures are published to all users by default. In order to create a private adventure a premium account is required.")
        }
    }
}