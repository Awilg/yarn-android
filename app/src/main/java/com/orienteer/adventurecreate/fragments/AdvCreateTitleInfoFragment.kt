package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.stickyheader.StickyHeaderCallbacks
import com.airbnb.epoxy.stickyheader.StickyHeaderLinearLayoutManager
import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.models.advCreateTextSection
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.createSectionHeader
import com.orienteer.databinding.FragmentAdvcreateTitleInfoBinding
import com.orienteer.util.BaseFragment

class AdvCreateTitleInfoFragment : BaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()
    lateinit var binding: FragmentAdvcreateTitleInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAdvcreateTitleInfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleinfoRecyclerView.withModels {
            createSectionHeader {
                id(hashCode())
                header("Name your adventure")
                subtitle("Be descriptive. Try to include what makes this adventure unique.")
            }

            advCreateTextSection {
                id(hashCode())
                prompt("Title")
                hint("e.g. The Great Orange Monster")
                charCount(50)
            }

            advCreateTextSection {
                id(hashCode())
                prompt("Description")
                hint("e.g. This adventure will take you across San Francisco on the hunt for the great Orange Monster, weather permitting.")
                charCount(200)
            }
        }
        binding.titleinfoRecyclerView.setItemSpacingDp(16)


        binding.actionButtonSection.detailActiveButton.setOnClickListener {
            viewModel.completeTitleInfo()
            this.findNavController().navigate(
                AdvCreateTitleInfoFragmentDirections.actionAdvCreateTitleInfoFragmentToAdvCreatePhotoFragment()
            )
        }
    }

    override fun invalidate() {
        binding.titleinfoRecyclerView.requestModelBuild()
    }
}