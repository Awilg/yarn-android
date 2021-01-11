package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.activityViewModel
import com.orienteer.R
import com.orienteer.adventurecreate.models.advCreateInputRow
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.buttonOutlined
import com.orienteer.createSectionHeader
import com.orienteer.databinding.FragmentAdvcreateCluesLocationBinding
import com.orienteer.mapviewStatic
import com.orienteer.models.ClueType
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvCreateClueLocationFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()
    lateinit var binding: FragmentAdvcreateCluesLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdvcreateCluesLocationBinding.inflate(inflater)
        recyclerView = binding.titleinfoRecyclerView
        recyclerView.setController(epoxyController)
        recyclerView.setItemSpacingDp(16)
        binding.actionButtonSection.detailActiveButton.setOnClickListener {
            viewModel.saveLocationClue()
            this.findNavController().navigate(
                AdvCreateClueLocationFragmentDirections.actionAdvCreateClueLocationFragmentToAdvCreateCluesSummaryFragment()
            )
        }
        return binding.root
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        createSectionHeader {
            id("id_section_location_clue")
            header("Location Clue Editor")
            subtitle("Location clues consist of a clue prompt, a solution that is a physical location, and some optional hints.")
        }

        advCreateInputRow {
            id("id_clue_prompt")
            text(state.currentLocCluePrompt)
            prompt("Clue Prompt")
            hint("e.g. This International Orange Icon connects San Francisco and Marin County")
            onEditTextChanged { viewModel.updateCluePrompt(it, ClueType.Location) }
        }

        state.currentLocClueLatLng?.let {
            mapviewStatic {
                id("mapview")
                latlng(it)
                mapsApiKey(getString(R.string.google_maps_key))
            }
        }

        buttonOutlined {
            id("id_choose_on_map_button")
            buttonText("Choose on map")
            onClickListener { _ ->
                findNavController().navigate(
                    AdvCreateClueLocationFragmentDirections.actionAdvCreateClueLocationFragmentToAdvCreateClueLocationSelectionFragment()
                )
            }
        }
    }
}