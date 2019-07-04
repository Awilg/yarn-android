package com.orienteer.treasurehunts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.orienteer.databinding.FragmentTreasureHuntsBinding

class TreasureHuntsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTreasureHuntsBinding.inflate(inflater)
        return binding.root
    }
}