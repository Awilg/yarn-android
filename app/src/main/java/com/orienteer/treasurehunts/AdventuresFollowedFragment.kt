package com.orienteer.treasurehunts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.orienteer.databinding.FragmentAdventuresFollowedBinding

class AdventuresFollowedFragment : Fragment() {

    /**
     * Lazily initialize our [AdventuresFollowedViewModel].
     */
    private val viewModel: AdventuresFollowedViewModel by lazy {
        ViewModelProviders.of(this).get(AdventuresFollowedViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentAdventuresFollowedBinding.inflate(inflater)

        binding.viewModel = viewModel

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        binding.adventuresFollowedCardList.adapter = AdventuresFollowedAdapter(AdventuresFollowedAdapter.OnClickListener {
            Toast.makeText(context, "Clicked adventure in progress ${it.adventureId}!", Toast.LENGTH_LONG).show()
        })

        //TODO: make this use the list returned from the viewmodel
//        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
//        binding.lifecycleOwner = this
//
//        binding.treasureHuntsCardList.adapter = TreasureHuntsAdapter(TreasureHuntsAdapter.OnClickListener {
//            Toast.makeText(context, "Clicked treasure hunt ${it.name}!", Toast.LENGTH_LONG).show()
//            viewModel.displayTreasureHuntDetails(it)
//        })
//
//        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
//        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
//        // for another navigation event.
//        viewModel.navigateToSelectedTreasureHunt.observe(this, Observer {
//            if ( null != it ) {
//                // Must find the NavController from the Fragment
//                this.findNavController().navigate(
//                    TreasureHuntsFragmentDirections.actionTreasureHuntsFragmentToTreasureHuntDetail(it))
//
//                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
//                viewModel.doneNavigatingToSelectedTreasureHunt()
//            }
//        })
        return binding.root
    }
}