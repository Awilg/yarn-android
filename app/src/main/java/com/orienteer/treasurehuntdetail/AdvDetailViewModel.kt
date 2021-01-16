package com.orienteer.treasurehuntdetail

import com.airbnb.mvrx.MavericksViewModel
import com.google.android.gms.maps.model.LatLng
import com.orienteer.models.Adventure
import com.orienteer.models.Clue
import com.orienteer.models.ClueState
import com.orienteer.models.ClueType

class AdvDetailViewModel(initialState: AdvDetailState) :
	MavericksViewModel<AdvDetailState>(initialState) {

	fun loadAdventure(adventureId: String) {
		// TODO: get correct adventure from DB
		setState {
			copy(
				adv = Adventure(
					"dummy",
					"A romp around South Lake Union",
					"Come see for yourself where the Amazonians live! Beware!",
					LatLng(47.6062, -122.3321),
					listOf(
						Clue(
							"clue_1",
							"This is a text clue",
							"This is a hint",
							ClueType.Text,
							LatLng(47.539, -122.305),
							ClueState.ACTIVE,
							"answer"
						)
					),
					"https://cdn.geekwire.com/wp-content/uploads/2016/04/20160423_Amazon_biodomes_73-630x421.jpg",
					listOf(
						"https://cdn.geekwire.com/wp-content/uploads/2016/04/20160423_Amazon_biodomes_73-630x421.jpg",
						"https://www.tripsavvy.com/thmb/PjesAcSOetIKsjSdINiJBW5kKgU=/4161x2341/smart/filters:no_upscale()/view-of-columbia-tower-142567995-5b10b2d68e1b6e0036cc5956.jpg",
						"https://www.visitseattle.org/wp-content/uploads/2015/03/VS_OVG_WS2019_Waterfront_credit-Shutterstock_web.jpg"
					)
				)
			)
		}
	}
}
