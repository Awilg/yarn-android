package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.orienteer.adventurecreate.controller.AdvCreateTitleInfoController
import com.orienteer.databinding.FragmentAdvcreateTitleInfoBinding

class AdvCreateTitleInfoFragment : Fragment() {

    private val viewModel: AdvCreateTitleInfoFragment by viewModels()
    lateinit var binding: FragmentAdvcreateTitleInfoBinding

    private val controller by lazy { AdvCreateTitleInfoController() }

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


        binding.titleinfoRecyclerView.setItemSpacingDp(16)
        binding.titleinfoRecyclerView.setControllerAndBuildModels(controller)
    }
}