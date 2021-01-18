package com.orienteer.treasurehunts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.LatLng
import com.orienteer.databinding.FragmentTreasureHuntsBinding

class TreasureHuntsFragment : Fragment() {

    /**
     * Lazily initialize our [TreasureHuntsViewModel].
     */
    private val viewModel: TreasureHuntsViewModel by lazy {
        ViewModelProviders.of(this).get(TreasureHuntsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTreasureHuntsBinding.inflate(inflater)

        // TODO: get location from device
        val viewModelFactory = TreasureHuntsViewModelFactory(LatLng(47.6062,122.3321))
        binding.viewModel = ViewModelProviders.of(this, viewModelFactory).get(TreasureHuntsViewModel::class.java)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        binding.treasureHuntsCardList.adapter = TreasureHuntsAdapter(TreasureHuntsAdapter.OnClickListener {
            Toast.makeText(context, "Clicked treasure hunt ${it.name}!", Toast.LENGTH_LONG).show()
            viewModel.displayTreasureHuntDetails(it)
        }, TreasureHuntsAdapter.AdventureViewHolder.Normal)

        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedAdventure.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                // Must find the NavController from the Fragment
//                this.findNavController().navigate(
//                    TreasureHuntsFragmentDirections.actionTreasureHuntsFragmentToTreasureHuntDetail(
//                        it.id
//                    )
//                )

                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.doneNavigatingToSelectedTreasureHunt()
            }
        })
        return binding.root
    }
}