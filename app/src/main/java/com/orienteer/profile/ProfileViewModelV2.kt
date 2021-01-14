package com.orienteer.profile

import com.airbnb.mvrx.MavericksViewModel

class ProfileViewModelV2(initialState: ProfileState) :
	MavericksViewModel<ProfileState>(initialState) {

	init {
		setState {
			copy(
				userName = "jimmy the kid",
				userImageUrl = "https://firebasestorage.googleapis.com/v0/b/orienteer-dev.appspot.com/o/IMG_20190702_100344.jpg?alt=media&token=5537d40d-e6d4-4b77-a30e-8c2d0bb11d04",
				userRank = "Navigation Novice"
			)
		}
	}
}
