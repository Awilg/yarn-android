package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.models.advCreateClueTypeSelection
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.createSectionHeader
import com.orienteer.databinding.FragmentAdvcreateCluesBinding
import com.orienteer.models.ClueType
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvCreateClueTypeSelectionFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()
    lateinit var binding: FragmentAdvcreateCluesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdvcreateCluesBinding.inflate(inflater)
        recyclerView = binding.titleinfoRecyclerView
        recyclerView.setController(epoxyController)
        recyclerView.setItemSpacingDp(16)
        binding.actionButtonSection.detailActiveButton.setOnClickListener {
            when (viewModel.currentClueTypeSelection.value) {
                ClueType.Text -> {
                    this.findNavController()
                        .navigate(AdvCreateClueTypeSelectionFragmentDirections.actionAdvCreateCluesFragmentToAdvCreateClueTextFragment())
                }
                ClueType.Photo -> {
                    this.findNavController()
                        .navigate(AdvCreateClueTypeSelectionFragmentDirections.actionAdvCreateCluesFragmentToAdvCreateCluePhotoFragment())
                }
                ClueType.Location -> {
                    this.findNavController()
                        .navigate(AdvCreateClueTypeSelectionFragmentDirections.actionAdvCreateCluesFragmentToAdvCreateClueLocationFragment())
                }
                else -> Toast.makeText(context, "Clue Type not yet supported", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return binding.root
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        createSectionHeader {
            id("clue_selection_section_header")
            header("What will this clue be?")
        }

        advCreateClueTypeSelection {
            id("clue_type_selection")
            onClueTypeUpdated {
                viewModel.updateCurrentClueTypeSelected(it)
            }
        }
    }
}