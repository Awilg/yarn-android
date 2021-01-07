package com.orienteer.adventurecreate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.createSectionHeader
import com.orienteer.databinding.FragmentAdvcreatePhotosBinding
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvCreatePhotoFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()
    lateinit var binding : FragmentAdvcreatePhotosBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdvcreatePhotosBinding.inflate(inflater)
        recyclerView = binding.photosRecyclerView
        recyclerView.setController(epoxyController)
        return binding.root
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        createSectionHeader {
            id(hashCode())
            header("Add some photos")
            subtitle("Draw people in with a taste of what they might see on your adventure")
        }
    }
}