package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.models.advCreateClueListItem
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.buttonOutlined
import com.orienteer.createSectionHeader
import com.orienteer.databinding.FragmentAdvcreateCluesBinding
import com.orienteer.label
import com.orienteer.models.ClueType
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController
import kotlin.random.Random

class AdvCreateCluesSummaryFragment : MavericksBaseFragment() {

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
            viewModel.saveClues()
            this.findNavController().navigate(
                AdvCreateCluesSummaryFragmentDirections.actionAdvCreateCluesSummaryFragmentToAdvCreateTipsFragment()
            )
        }
        return binding.root
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        createSectionHeader {
            id("clue_section_header")
            header("Clues")
            subtitle("This is the meat and potatoes of your adventure. Like a fine 3 (or 20) course meal.")
        }

        state.clues.forEach {
            advCreateClueListItem {
                id(Random.nextInt())
                prompt(it.cluePrompt)
                clueType(ClueType.valueOf(it.type))
            }
        }

        if (state.clues.isEmpty()) {
            label {
                id("clue_section_sub")
                text("There are no clues yet. Add one!")
            }
        }

        buttonOutlined {
            id("add_clue_btn")
            buttonText("Add")
            onClickListener { _ ->
                findNavController().navigate(
                    AdvCreateCluesSummaryFragmentDirections.actionAdvCreateCluesSummaryFragmentToAdvCreateCluesFragment()
                )
            }
        }

    }
}