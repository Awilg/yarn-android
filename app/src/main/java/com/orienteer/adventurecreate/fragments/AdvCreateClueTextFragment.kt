package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.models.advCreateInputRow
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.createSectionHeader
import com.orienteer.databinding.FragmentAdvcreateCluesTextEditorBinding
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvCreateClueTextFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()
    lateinit var binding: FragmentAdvcreateCluesTextEditorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdvcreateCluesTextEditorBinding.inflate(inflater)
        recyclerView = binding.titleinfoRecyclerView
        recyclerView.setController(epoxyController)
        binding.actionButtonSection.detailActiveButton.setOnClickListener {
            viewModel.saveTextClue()
            this.findNavController().navigate(
                AdvCreateClueTextFragmentDirections.actionAdvCreateClueTextFragmentToAdvCreateCluesSummaryFragment()
            )
        }
        return binding.root
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        createSectionHeader {
            id("id_section_text_clue")
            header("Text Clue Editor")
            subtitle("Text clues consist of a clue prompt, a solution, and some optional hints.")
        }

        advCreateInputRow {
            id("id_clue_prompt")
            text(state.currentTextCluePrompt)
            prompt("Clue Prompt")
            hint("e.g. This International Orange Icon connects San Francisco and Marin County")
            onEditTextChanged { viewModel.updateTextCluePrompt(it) }
        }

        advCreateInputRow {
            id("id_clue_answer")
            text(state.currentTextClueAnswer)
            prompt("Clue Prompt")
            hint("e.g. The Golden Gate Bridge")
            onEditTextChanged { viewModel.updateTextClueAnswer(it) }
        }
    }
}