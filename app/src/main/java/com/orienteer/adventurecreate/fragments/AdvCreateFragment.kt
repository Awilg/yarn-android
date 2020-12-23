package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.orienteer.adventurecreate.AdvCreateEpoxyController
import com.orienteer.adventurecreate.AdvCreateViewModel
import com.orienteer.databinding.FragmentAdvcreateBinding
import com.orienteer.util.TEST_ADV_CREATE_SUMMARY_COMPLETED
import com.orienteer.util.TEST_ADV_CREATE_SUMMARY_IN_PROGRESS


class AdvCreateFragment : Fragment() {

    private lateinit var viewModel : AdvCreateViewModel
    private lateinit var binding: FragmentAdvcreateBinding

    private val controller by lazy { AdvCreateEpoxyController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdvcreateBinding.inflate(inflater)

        viewModel = activity?.run {
            ViewModelProviders.of(this)[AdvCreateViewModel::class.java]
        }!!

        // To use the binding with databinding you need to explicitly give the binding a reference to it.
        binding.viewmodel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inprogressRecyclerView.setController(controller)
        controller.inProgressAdvCreate = TEST_ADV_CREATE_SUMMARY_IN_PROGRESS
        controller.completedAdvCreate = TEST_ADV_CREATE_SUMMARY_COMPLETED
    }
}