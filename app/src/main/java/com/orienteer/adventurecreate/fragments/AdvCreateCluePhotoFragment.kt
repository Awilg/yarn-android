package com.orienteer.adventurecreate.fragments

import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.createSectionHeader
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvCreateCluePhotoFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()


    override fun epoxyController() = simpleController(viewModel) { state ->
        createSectionHeader {
            id("id_section_photo_clue")
            header("Photo Clue Editor")
            subtitle("Photo clues consist of a clue prompt, photos of the solution (a landmark, object, famous person, etc.) and some optional hints.")
        }
    }
}