package com.orienteer.active

import android.os.Bundle
import com.airbnb.mvrx.activityViewModel
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvActiveFragment : MavericksBaseFragment() {

    private val viewModel: AdvActiveViewModel by activityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adventure = AdvActiveFragmentArgs.fromBundle(requireArguments()).currentAdventure
        viewModel.setAdventure(adventure)
    }

    override fun epoxyController() = simpleController(viewModel) {

    }
}