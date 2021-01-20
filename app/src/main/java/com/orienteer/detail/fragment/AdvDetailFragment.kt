package com.orienteer.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.activityViewModel
import com.orienteer.*
import com.orienteer.databinding.FragmentAdvdetailBinding
import com.orienteer.detail.model.advDetailClueSummary
import com.orienteer.detail.model.advDetailGallery
import com.orienteer.detail.viewmodel.AdvDetailViewModel
import com.orienteer.models.ClueType
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.TEST_PARAGRAPH
import com.orienteer.util.simpleController

class AdvDetailFragment : MavericksBaseFragment() {

    private val viewModel: AdvDetailViewModel by activityViewModel()
    lateinit var binding: FragmentAdvdetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adventureId = AdvDetailFragmentArgs.fromBundle(requireArguments()).adventureId
        viewModel.loadAdventure(adventureId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdvdetailBinding.inflate(inflater)
        recyclerView = binding.recyclerView
        recyclerView.setController(epoxyController)
        recyclerView.setItemSpacingDp(16)
        binding.actionButtonSection.detailActiveButton.setOnClickListener {
            findNavController().navigate(
                AdvDetailFragmentDirections.actionAdvDetailFragmentToAdvActiveFragment(
                    viewModel.getAdventure()
                )
            )
        }
        return binding.root
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
                onClick { _ ->
                    findNavController().navigate(
                        AdvDetailFragmentDirections.actionAdvDetailFragmentToAdvActiveFragment(
                            state.adventure
                        )
                    )
                }
            }

            mapviewStatic {
                id("mapview1")
                latlng(state.adventure.location)
                mapsApiKey(getString(R.string.google_maps_key))
            }
        }
    }
}