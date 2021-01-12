package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.models.SectionItem
import com.orienteer.adventurecreate.models.SectionType
import com.orienteer.adventurecreate.models.initialSectionList
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.createSummaryHeader
import com.orienteer.createSummaryItem
import com.orienteer.createSummaryItemCurrent
import com.orienteer.databinding.FragmentAdvcreateSummaryBinding
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvCreateSummaryFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()
    lateinit var binding: FragmentAdvcreateSummaryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdvcreateSummaryBinding.inflate(inflater)
        recyclerView = binding.summaryRecyclerView
        recyclerView.setController(epoxyController)
        recyclerView.setItemSpacingDp(16)

        return binding.root
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        createSummaryHeader {
            id(hashCode())
            header("Let's make something together")
        }

        // TODO: review backstack pop nav for the create flow
        initialSectionList.forEach { sectionItem ->
            var itemFocused = false
            var itemContinuable = false
            var itemDone = false

            if (state.currentFocusedSectionItem == sectionItem.type) {
                itemFocused = true
            } else {

                when (sectionItem.type) {
                    SectionType.TitleAndInfo -> {
                        if (!state.title.isNullOrEmpty() && !state.description.isNullOrEmpty()) {
                            itemDone = true
                        } else if ((!state.title.isNullOrEmpty() && state.description.isNullOrEmpty()) || (state.title.isNullOrEmpty() && !state.description.isNullOrEmpty())) {
                            itemContinuable = true
                        }
                    }
                    SectionType.Photos -> {
                        if (state.photos.isNotEmpty()) {
                            itemContinuable = true
                        }
                    }
                    SectionType.Clues -> {
                        if (state.clues.isNotEmpty()) {
                            itemContinuable = true
                        }
                    }
                    SectionType.Publishing -> {
                    }
                    SectionType.TipsAndTreasure -> {
                    }
                    SectionType.Review -> {
                    }
                }

            }


            if (itemFocused) {
                createSummaryItemCurrent {
                    id(hashCode())
                    item(sectionItem)
                    onClick { _ ->
                        doNavForType(sectionItem)
                    }
                }
            } else {
                createSummaryItem {
                    id(hashCode())
                    item(sectionItem)
                    completed(itemDone)
                    onClick { _ ->
                        doNavForType(sectionItem)
                    }
                }
            }
        }
    }


    private fun doNavForType(type: SectionItem) =
        when (type.type) {
            SectionType.TitleAndInfo -> navToTitleAndInfo()
            SectionType.Photos -> navToPhotos()
            SectionType.Clues -> navToClues()
            SectionType.Publishing -> navToPublishing()
            SectionType.TipsAndTreasure -> navToTips()
            SectionType.Review -> navToReview()
        }


    private fun navToTitleAndInfo() {
        this.findNavController().navigate(
            AdvCreateSummaryFragmentDirections.actionAdvCreateSummaryFragmentToAdvCreateTitleInfoFragment()
        )
    }

    private fun navToPhotos() {
        this.findNavController().navigate(
            AdvCreateSummaryFragmentDirections.actionAdvCreateSummaryFragmentToAdvCreatePhotoFragment()
        )
    }

    private fun navToClues() {
        this.findNavController().navigate(
            AdvCreateSummaryFragmentDirections.actionAdvCreateSummaryFragmentToAdvCreateCluesSummaryFragment()
        )
    }

    private fun navToPublishing() {
        this.findNavController().navigate(
            AdvCreateSummaryFragmentDirections.actionAdvCreateSummaryFragmentToAdvCreateCluesSummaryFragment()
        )
    }

    private fun navToTips() {
        this.findNavController().navigate(
            AdvCreateSummaryFragmentDirections.actionAdvCreateSummaryFragmentToAdvCreateTipsFragment()
        )
    }

    private fun navToReview() {

        this.findNavController().navigate(
            AdvCreateSummaryFragmentDirections.actionAdvCreateSummaryFragmentToAdvCreateReviewFragment()
        )
    }
}
