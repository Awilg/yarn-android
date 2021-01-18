package com.orienteer.detail.fragment

import android.os.Bundle
import com.airbnb.mvrx.activityViewModel
import com.orienteer.*
import com.orienteer.databinding.FragmentAdvcreateReviewBinding
import com.orienteer.detail.model.advDetailClueSummary
import com.orienteer.detail.model.advDetailGallery
import com.orienteer.detail.viewmodel.AdvDetailViewModel
import com.orienteer.models.ClueType
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.TEST_PARAGRAPH
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

        state.adventure?.let {

            advDetailGallery {
                id("gallery")
                adventureImages(state.adventure.featuredImageList)
            }

            advdetailMainInfo {
                id("mainInfo")
                adventure(state.adventure)
            }

            advDetailClueSummary {
                id("clueSummary")
                authorImage(state.adventure.featuredImage)
                numTextClues(state.adventure.clues.filter { it.type == ClueType.Text }.size)
                numPhotoClues(state.adventure.clues.filter { it.type == ClueType.Photo }.size)
                numLocationClues(state.adventure.clues.filter { it.type == ClueType.Location }.size)
            }

            textSummary {
                id("textDescription")
                text(TEST_PARAGRAPH)
            }

            mapviewStatic {
                id("mapview")
                latlng(state.adventure.location)
                mapsApiKey(getString(R.string.google_maps_key))
            }

            advdetailReviews {
                id("reviews")
            }
        }
    }
}