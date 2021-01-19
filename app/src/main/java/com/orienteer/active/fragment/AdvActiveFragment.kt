package com.orienteer.active.fragment

import android.os.Bundle
import com.airbnb.mvrx.activityViewModel
import com.orienteer.active.model.advActiveCompletedClue
import com.orienteer.active.model.advActiveCurrentClue
import com.orienteer.active.viewmodel.AdvActiveViewModel
import com.orienteer.clueLocked
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController

class AdvActiveFragment : MavericksBaseFragment() {

    private val viewModel: AdvActiveViewModel by activityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adventure = AdvActiveFragmentArgs.fromBundle(requireArguments()).currentAdventure
        viewModel.setAdventure(adventure)
    }

    override fun epoxyController() = simpleController(viewModel) { state ->

        state.adventure?.let {

            advActiveCompletedClue {
                id("completed")
                clue(it.clues.first())
            }

            advActiveCurrentClue {
                id("current")
                clue(it.clues.first())
            }
            clueLocked {
                id("clue_locked")
            }

        }
    }
}