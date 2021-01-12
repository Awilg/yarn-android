package com.orienteer.adventurecreate.fragments

import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.models.advCreateToggleItem
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.createSectionHeader
import com.orienteer.createTreasureSlider
import com.orienteer.databinding.FragmentAdvcreatePhotosBinding
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvCreateTipsFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()
    lateinit var binding : FragmentAdvcreatePhotosBinding

    override fun epoxyController() = simpleController(viewModel) { state ->
        createSectionHeader {
            id(hashCode())
            header("Tips & Treasure")
            subtitle("What's an adventure without some treasure? Here you can stash the spoils as well as allow other adventurers to leave you tips for an adventure well crafted.")
        }

        advCreateToggleItem {
            id(hashCode())
            text("Enable tips")
        }

        advCreateToggleItem {
            id(hashCode())
            text("Add a treasure")
        }

        createTreasureSlider {
            id(hashCode())
            booty(500)
        }

    }
}