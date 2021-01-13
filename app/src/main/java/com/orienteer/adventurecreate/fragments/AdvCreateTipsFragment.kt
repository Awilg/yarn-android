package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.models.advCreateToggleItem
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.createSectionHeader
import com.orienteer.createTreasureSlider
import com.orienteer.databinding.FragmentAdvcreateTipsBinding
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvCreateTipsFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()
    lateinit var binding: FragmentAdvcreateTipsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdvcreateTipsBinding.inflate(inflater)
        recyclerView = binding.titleinfoRecyclerView
        recyclerView.setController(epoxyController)
        recyclerView.setItemSpacingDp(16)
        binding.actionButtonSection.detailActiveButton.setOnClickListener {
            viewModel.saveTips()
            this.findNavController().navigate(
                AdvCreateTipsFragmentDirections.actionAdvCreateTipsFragmentToAdvCreateReviewFragment()
            )
        }
        return binding.root
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        createSectionHeader {
            id("section_header")
            header("Tips & Treasure")
            subtitle("What's an adventure without some treasure? Here you can stash the spoils as well as allow other adventurers to leave you tips for an adventure well crafted.")
        }

        advCreateToggleItem {
            id("tips_toggle")
            text("Enable tips")
            enabled(state.tipsEnabled)
            onToggleItem { viewModel.updateTipsEnabled(it) }
        }

        advCreateToggleItem {
            id("treasure_toggle")
            text("Add a treasure")
            enabled(state.treasureEnabled)
            onToggleItem { viewModel.updateTreasureEnabled(it) }
        }

        state.treasureEnabled?.let {
            createTreasureSlider {
                id("tips_slider")
                booty(500)
            }
        }
    }
}