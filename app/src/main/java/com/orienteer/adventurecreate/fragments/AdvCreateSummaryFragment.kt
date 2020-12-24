package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.orienteer.adventurecreate.controller.AdvCreateSummaryController
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.databinding.FragmentAdvcreateSummaryBinding

class AdvCreateSummaryFragment : Fragment() {

    private val viewModel: AdvCreateSummaryViewModel by viewModels()
    lateinit var binding : FragmentAdvcreateSummaryBinding

    private val controller by lazy { AdvCreateSummaryController(viewModel) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAdvcreateSummaryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navToTitleAndInfo.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Whatchamp??", Toast.LENGTH_SHORT).show()
            if (null != it && it) {
//                this.findNavController().navigate(
//                    AdvCreateFragmentDirections.actionAdvCreateFragmentToAdvCreateSummaryFragment()
//                )

                viewModel.doneNavToTitleAndInfo()
            }
        }


        binding.summaryRecyclerView.setItemSpacingDp(16)
        binding.summaryRecyclerView.setController(controller)

        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            controller.setData(viewState)
        }
    }
}