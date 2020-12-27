package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.controller.AdvCreateSummaryController
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.databinding.FragmentAdvcreateSummaryBinding
import com.orienteer.util.BaseFragment

class AdvCreateSummaryFragment : BaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()
    lateinit var binding : FragmentAdvcreateSummaryBinding

    private val controller by lazy { AdvCreateSummaryController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAdvcreateSummaryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navToTitleAndInfo.observe(viewLifecycleOwner) {
            if (null != it && it) {
                this.findNavController().navigate(
                    AdvCreateSummaryFragmentDirections.actionAdvCreateSummaryFragmentToAdvCreateTitleInfoFragment()
                )

                viewModel.doneNavToTitleAndInfo()
            }
        }


        binding.summaryRecyclerView.setItemSpacingDp(16)
        binding.summaryRecyclerView.setController(controller)

        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            controller.setData(viewState)
        }
    }

    override fun invalidate() {

    }
}