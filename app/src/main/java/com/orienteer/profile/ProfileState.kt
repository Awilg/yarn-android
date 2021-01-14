package com.orienteer.profile

import com.airbnb.mvrx.MavericksState

data class ProfileState(
	val userId: String? = null,
	val userImageUrl: String? = null,
	val userName: String? = null,
	val userRank: String? = null
) : MavericksState
