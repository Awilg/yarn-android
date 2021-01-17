package com.orienteer.detail.fragment

import android.os.Bundle
import com.airbnb.mvrx.activityViewModel
import com.orienteer.advdetailCluesOverview
import com.orienteer.advdetailMainInfo
import com.orienteer.databinding.FragmentAdvcreateReviewBinding
import com.orienteer.detail.viewmodel.AdvDetailViewModel
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvDetailFragment : MavericksBaseFragment() {

    private val viewModel: AdvDetailViewModel by activityViewModel()
    lateinit var binding: FragmentAdvcreateReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adventureId = AdvDetailFragmentArgs.fromBundle(requireArguments()).adventureId
        viewModel.getAdventure(adventureId)
    }

    override fun epoxyController() = simpleController(viewModel) { state ->

        advdetailMainInfo {
            id("mainInfo")
            adventure(state.adventure)
        }

        advdetailCluesOverview {
            id("cluesOverview")
            adventure(state.adventure)
        }
    }
}