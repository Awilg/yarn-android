package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.orienteer.adventurecreate.controller.AdvCreateEpoxyController
import com.orienteer.adventurecreate.viewmodel.AdvCreateViewModel
import com.orienteer.databinding.FragmentAdvcreateBinding
import com.orienteer.util.TEST_ADV_CREATE_SUMMARY_COMPLETED
import com.orienteer.util.TEST_ADV_CREATE_SUMMARY_IN_PROGRESS


class AdvCreateFragment : Fragment() {

    private val viewModel : AdvCreateViewModel by viewModels()
    private lateinit var binding: FragmentAdvcreateBinding

    private val controller by lazy { AdvCreateEpoxyController(viewModel) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdvcreateBinding.inflate(inflater)

        // To use the binding with databinding you need to explicitly give the binding a reference to it.
        binding.viewmodel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigateToCreateNewAdventure.observe(viewLifecycleOwner, {
            if (null != it && it) {
                this.findNavController().navigate(
                    AdvCreateFragmentDirections.actionAdvCreateFragmentToAdvCreateSummaryFragment()
                )

                viewModel.doneNavigateToCreateNewAdventure()
            }
        })

        binding.inprogressRecyclerView.setItemSpacingDp(16)
        binding.inprogressRecyclerView.setController(controller)

        // Combine these to a data object that holds them all and the states
        controller.inProgressAdvCreate = TEST_ADV_CREATE_SUMMARY_IN_PROGRESS
        controller.completedAdvCreate = TEST_ADV_CREATE_SUMMARY_COMPLETED
    }
}