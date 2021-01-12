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
import com.orienteer.databinding.FragmentAdvcreatePublishingBinding
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvCreatePublishingFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()
    lateinit var binding: FragmentAdvcreatePublishingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdvcreatePublishingBinding.inflate(inflater)
        recyclerView = binding.titleinfoRecyclerView
        recyclerView.setController(epoxyController)
        recyclerView.setItemSpacingDp(16)
        binding.actionButtonSection.detailActiveButton.setOnClickListener {
            viewModel.savePublishing()
            this.findNavController().navigate(
                AdvCreatePublishingFragmentDirections.actionAdvCreatePublishingFragmentToAdvCreateCluesSummaryFragment()
            )
        }
        return binding.root
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        createSectionHeader {
            id("section_header")
            header("Publishing")
            subtitle("All adventures are published to all users by default. In order to create a private adventure a premium account is required.")
        }

        advCreateToggleItem {
            id("toggle_private")
            text("Publish Private adventure")
            enabled(state.isPrivateAdventure)
            onToggleItem { viewModel.updateAdventurePrivacy(it) }
        }

    }
}