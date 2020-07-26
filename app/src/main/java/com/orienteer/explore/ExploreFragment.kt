package com.orienteer.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.orienteer.R
import com.orienteer.core.OnSnapPositionChangeListener
import com.orienteer.core.SnapOnScrollListener
import com.orienteer.core.attachSnapHelperWithListener
import com.orienteer.databinding.FragmentExploreBinding
import com.orienteer.map.MapFragmentDirections
import com.orienteer.treasurehunts.TreasureHuntsAdapter

class ExploreFragment : Fragment() {

    private val _viewModel : ExploreViewModel by viewModels()

    private lateinit var _binding: FragmentExploreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater)
        _binding.viewModel = _viewModel
        _binding.lifecycleOwner = this


        // Create the adapter that will populate the recycler view and updates the livedata
        // in the viewmodel to determine where to navigate.
        _binding.nearbyAdventureContainer.treasureHuntsCardsMapRecyclerView.adapter =
            TreasureHuntsAdapter(TreasureHuntsAdapter.OnClickListener {
                _viewModel.displayTreasureHuntDetails(it)
            }, TreasureHuntsAdapter.AdventureViewHolder.Featured)

        _binding.solveFromHomeContainer.solveFromHomeRecyclerView.adapter =
            TreasureHuntsAdapter(TreasureHuntsAdapter.OnClickListener {
                _viewModel.displayTreasureHuntDetails(it)
            }, TreasureHuntsAdapter.AdventureViewHolder.Bottomless)

        // Adds a listener that is aware of "swipe" card changes to the underlying RecyclerView
        // Snaps cards to the screen so there's only one ever fully on screen and moves the map to
        // the location of the hunt currently on screen.
        _binding.nearbyAdventureContainer.treasureHuntsCardsMapRecyclerView.attachSnapHelperWithListener(
            PagerSnapHelper(),
            SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE,
            object : OnSnapPositionChangeListener {
                override fun onSnapPositionChange(position: Int) {
                    val adapter = _binding.nearbyAdventureContainer.treasureHuntsCardsMapRecyclerView.adapter as TreasureHuntsAdapter
                    val hunt = adapter.getItem(position)
                    //_viewModel.moveMapToLocation(hunt.location)
                    // TODO - Figure out if this is needed or just a pager thing
                }
            }
        )

        _binding.solveFromHomeContainer.solveFromHomeRecyclerView.attachSnapHelperWithListener(
            PagerSnapHelper(),
            SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE,
            object : OnSnapPositionChangeListener {
                override fun onSnapPositionChange(position: Int) {
                    val adapter = _binding.solveFromHomeContainer.solveFromHomeRecyclerView.adapter as TreasureHuntsAdapter
                    val hunt = adapter.getItem(position)
                    //_viewModel.moveMapToLocation(hunt.location)
                    // TODO - Figure out if this is needed or just a pager thing
                }
            }
        )

        // Properly handle the margins for the recyclerview
        _binding.nearbyAdventureContainer.treasureHuntsCardsMapRecyclerView.addItemDecoration(
            MarginItemDecoration(
                resources.getDimensionPixelSize(R.dimen.margin_24),
                resources.getDimensionPixelSize(R.dimen.margin_16)
            )
        )

        _binding.solveFromHomeContainer.solveFromHomeRecyclerView.addItemDecoration(
            MarginItemDecoration(
                resources.getDimensionPixelSize(R.dimen.margin_0),
                resources.getDimensionPixelSize(R.dimen.margin_16)
            )
        )

        // Observe the navigateToSelectedTreasureHunt LiveData and Navigate when it isn't null
        // After navigating, call doneNavigatingToSelectedTreasureHunt() so that the ViewModel is ready
        // for another navigation event.
        _viewModel.navigateToSelectedAdventure.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(
                    MapFragmentDirections.actionMapDestinationToTreasureHuntDetail(it)
                )

                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                _viewModel.doneNavigatingToSelectedTreasureHunt()
            }
        })
        return _binding.root
    }
}