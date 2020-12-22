package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.orienteer.adventurecreate.AdvCreateViewModel
import com.orienteer.databinding.FragmentAdvcreateBinding
import com.orienteer.databinding.FragmentExploreBinding


class AdvCreateFragment : Fragment() {

    private lateinit var viewModel : AdvCreateViewModel
    private lateinit var binding: FragmentExploreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAdvcreateBinding.inflate(inflater)

        viewModel = activity?.run {
            ViewModelProviders.of(this)[AdvCreateViewModel::class.java]
        }!!



        // To use the binding with databinding you need to explicitly give the binding a reference to it.
        binding.viewmodel = viewModel

        return binding.root
    }
}