package com.orienteer.treasurehuntdetail

import android.os.Bundle
import com.airbnb.mvrx.activityViewModel
import com.orienteer.databinding.FragmentAdvcreateReviewBinding
import com.orienteer.detailGallery
import com.orienteer.detailMainInfo
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvDetailFragment : MavericksBaseFragment() {

	private val viewModel: AdvDetailViewModel by activityViewModel()
	lateinit var binding: FragmentAdvcreateReviewBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel.loadAdventure(AdvDetailFragmentArgs.fromBundle(requireArguments()).adventureId)
	}


	override fun epoxyController() = simpleController(viewModel) { state ->

		detailGallery {
			id("gallery")
			adventure(state.adv)
		}

		detailMainInfo {
			id("mainInfo")
			adventure(state.adv)
		}
	}
}
