package com.orienteer.adventurecreate.fragments

import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvCreateReviewFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()

    override fun epoxyController() = simpleController(viewModel) { state ->

    }
}