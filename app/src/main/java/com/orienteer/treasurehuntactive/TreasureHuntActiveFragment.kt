package com.orienteer.treasurehuntactive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.orienteer.core.ClueAdapter
import com.orienteer.databinding.FragmentTreasureHuntActiveBinding

class TreasureHuntActiveFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTreasureHuntActiveBinding.inflate(inflater)
        val application = requireNotNull(activity).application

        binding.lifecycleOwner = this

        binding.cluesRecyclerview.adapter = ClueAdapter()

        val treasureHunt = TreasureHuntActiveFragmentArgs.fromBundle(arguments!!).selectedTreasureHunt
        val viewModelFactory = TreasureHuntActiveViewModelFactory(treasureHunt, application)

        binding.viewModel = ViewModelProviders.of(this, viewModelFactory).get(TreasureHuntActiveViewModel::class.java)
        return binding.root
    }
}