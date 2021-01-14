package com.orienteer.profile

import com.airbnb.mvrx.activityViewModel
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class ProfileFragmentV2 : MavericksBaseFragment() {

	private val viewModel: ProfileViewModelV2 by activityViewModel()

	override fun epoxyController() = simpleController(viewModel) { state ->

	}
}
