package com.orienteer.treasurehuntdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.orienteer.databinding.FragmentTreasureHuntDetailBinding
import kotlinx.android.synthetic.main.detail_action_buttons.view.*

class TreasureHuntDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTreasureHuntDetailBinding.inflate(inflater)
        val application = requireNotNull(activity).application

        binding.lifecycleOwner = this

        val treasureHunt = TreasureHuntDetailFragmentArgs.fromBundle(arguments!!).selectedTreasureHunt
        val viewModelFactory = TreasureHuntDetailViewModelFactory(treasureHunt, application)

        binding.viewModel = ViewModelProviders.of(this, viewModelFactory).get(TreasureHuntDetailViewModel::class.java)

        binding.actionButtonSection.detail_active_button.setOnClickListener {
            // Must find the NavController from the Fragment
            this.findNavController().navigate(
                TreasureHuntDetailFragmentDirections.actionTreasureHuntDetailToTreasureHuntActiveFragment(treasureHunt))
        }

        binding.actionButtonSection.detail_follow_button.setOnClickListener {
            Toast.makeText(context, "Followed Adventure ${treasureHunt.name}!", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}