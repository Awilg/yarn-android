package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.createSectionHeader
import com.orienteer.databinding.FragmentAdvcreateReviewBinding
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvCreateReviewFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()
    lateinit var binding: FragmentAdvcreateReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdvcreateReviewBinding.inflate(inflater)
        recyclerView = binding.titleinfoRecyclerView
        recyclerView.setController(epoxyController)
        recyclerView.setItemSpacingDp(16)
        binding.actionButtonSection.detailActiveButton.setOnClickListener {
            viewModel.savePublishing()
            this.findNavController().navigate(
                AdvCreateReviewFragmentDirections.actionAdvCreateReviewFragmentToAdvCreateFragment()
            )
        }
        return binding.root
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        createSectionHeader {
            id("section_header")
            header("Review")
            subtitle("This is just a placeholder. The review should be a detail page basically. TBD.")
        }
    }
}