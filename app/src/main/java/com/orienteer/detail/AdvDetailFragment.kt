package com.orienteer.detail

import android.os.Bundle
import com.airbnb.mvrx.activityViewModel
import com.orienteer.databinding.FragmentAdvcreateReviewBinding
import com.orienteer.searchBar
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvDetailFragment : MavericksBaseFragment() {

    private val viewModel: AdvDetailViewModel by activityViewModel()
    lateinit var binding: FragmentAdvcreateReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adventureId = AdvDetailFragmentArgs.fromBundle(requireArguments()).adventureId
        viewModel.getAdventure(adventureId)
    }

    override fun epoxyController() = simpleController(viewModel) { state ->

        searchBar {
            id("search_bar")
        }
    }
}