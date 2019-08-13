package com.orienteer.treasurehuntactive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.orienteer.core.ClueAdapter
import com.orienteer.core.ClueAdapterListener
import com.orienteer.databinding.FragmentTreasureHuntActiveBinding
import com.orienteer.models.ClueType

class TreasureHuntActiveFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTreasureHuntActiveBinding.inflate(inflater)
        val application = requireNotNull(activity).application

        binding.lifecycleOwner = this

        binding.cluesRecyclerview.adapter = ClueAdapter(object : ClueAdapterListener {
            override fun clueTypeOnClick(type : ClueType) {
                Toast.makeText(context, "Clicked Type: ${type.name}", Toast.LENGTH_SHORT).show()
            }

            override fun clueSolveOnClick(type: ClueType) {
                Toast.makeText(context, "Clicked Solve!", Toast.LENGTH_SHORT).show()
            }

            override fun clueHintOnClick(hint : String) {
                Toast.makeText(context, "Clicked Hint: $hint", Toast.LENGTH_SHORT).show()
            }
        })

        val treasureHunt = TreasureHuntActiveFragmentArgs.fromBundle(arguments!!).selectedTreasureHunt
        val viewModelFactory = TreasureHuntActiveViewModelFactory(treasureHunt, application)

        binding.viewModel = ViewModelProviders.of(this, viewModelFactory).get(TreasureHuntActiveViewModel::class.java)
        return binding.root
    }
}
