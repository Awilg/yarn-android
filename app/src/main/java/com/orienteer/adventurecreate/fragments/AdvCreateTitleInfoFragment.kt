package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.models.advCreateTextSection
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
            viewModel.updateTitle("testTitle")

            this.findNavController().navigate(
                AdvCreateTitleInfoFragmentDirections.actionAdvCreateTitleInfoFragmentToAdvCreatePhotoFragment()
            )
        }
        return binding.root
    }

    override fun epoxyController() = simpleController(viewModel) { state ->

        Timber.i("Building Models: ${Thread.currentThread().name}")

        createSectionHeader {
            id(hashCode())
            header("Name your adventure")
            subtitle("Be descriptive. Try to include what makes this adventure unique.")
        }

        advCreateTextSection {
            id(hashCode())
            prompt(state.title)
            value(viewModel.currentTitle)
            hint("e.g. The Great Orange Monster")
            charCount(viewModel.titleCharCountRemaining)
            textWatcher(
                object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        viewModel.updateTitle(s.toString())
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })
        }

        advCreateTextSection {
            id(hashCode())
            prompt("Description")
            value("")
            hint("e.g. This adventure will take you across San Francisco on the hunt for the great Orange Monster, weather permitting.")
            charCount(viewModel.descriptionCharCountRemaining)
            textWatcher(
                object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        viewModel.updateDescription(s.toString())
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })
        }
    }
}