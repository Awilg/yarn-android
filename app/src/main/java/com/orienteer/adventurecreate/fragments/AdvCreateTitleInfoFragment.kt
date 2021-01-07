package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.models.advCreateInputRow
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.createSectionHeader
import com.orienteer.databinding.FragmentAdvcreateTitleInfoBinding
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController
import timber.log.Timber

class AdvCreateTitleInfoFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()
    lateinit var binding: FragmentAdvcreateTitleInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdvcreateTitleInfoBinding.inflate(inflater)
        recyclerView = binding.titleinfoRecyclerView
        recyclerView.setController(epoxyController)
        recyclerView.setItemSpacingDp(16)
        binding.actionButtonSection.detailActiveButton.setOnClickListener {
            this.findNavController().navigate(
                AdvCreateTitleInfoFragmentDirections.actionAdvCreateTitleInfoFragmentToAdvCreatePhotoFragment()
            )
        }
        return binding.root
    }

    override fun epoxyController() = simpleController(viewModel) { state ->

        Timber.i("Building Models: ${Thread.currentThread().name}")

        createSectionHeader {
            id("id_section_title")
            header(state.title)
            subtitle("Be descriptive. Try to include what makes this adventure unique.")
        }

        advCreateInputRow {
            id("id_title_input_row")
            text(state.title)
            prompt("Title")
            hint("e.g. The Orange Monster")
            onEditTextChanged { viewModel.updateTitle(it) }
        }

        advCreateInputRow {
            id("id_description_input_row")
            text(state.description)
            prompt("Description")
            hint("e.g. Think you know all there is to know about this looming landmark? Think again...")
            onEditTextChanged { viewModel.updateDescription(it) }
        }

    }
}