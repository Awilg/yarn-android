package com.orienteer.profile

import com.airbnb.mvrx.activityViewModel
import com.orienteer.profileSectionHeader
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class ProfileFragment : MavericksBaseFragment() {
	private val viewModel: ProfileViewModel by activityViewModel()

	override fun epoxyController() = simpleController(viewModel) { state ->
		profileSectionHeader {
			id("header")
			userName(state.userName)
			userRank(state.userRank)
			avatarUrl(state.userImageUrl)
		}
	}
}
