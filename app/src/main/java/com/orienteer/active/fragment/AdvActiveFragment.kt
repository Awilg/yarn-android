package com.orienteer.active.fragment

import android.os.Bundle
import android.widget.Toast
import com.airbnb.mvrx.activityViewModel
import com.orienteer.active.model.advActiveCompletedClue
import com.orienteer.active.model.advActiveCurrentClue
import com.orienteer.active.viewmodel.AdvActiveViewModel
import com.orienteer.advactiveHeader
import com.orienteer.buttonFull
import com.orienteer.clueLocked
import com.orienteer.models.ClueType
import com.orienteer.treasureChest
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

            advactiveHeader {
                id("header")
                header(it.name)
            }

            for ((index, clue) in it.clues.withIndex()) {
                when {
                    index < state.currentClueIndex -> {
                        advActiveCompletedClue {
                            id("completed")
                            clue(clue)
                        }
                    }
                    index == state.currentClueIndex -> {
                        advActiveCurrentClue {
                            id("current")
                            clue(clue)
                            onActionButton { _ ->
                                doActionForType(clue.type)
                            }
                        }
                    }
                    else -> {
                        clueLocked {
                            id("clue_locked")
                        }

                    }
                }

            }

            treasureChest {
                id("treasureChestImage")
            }

            buttonFull {
                id("finishBtn")
                buttonText("Finish")
                onClickListener { _ ->
                    Toast.makeText(context, "Finished Adventure!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun doActionForType(type: ClueType) =
        when (type) {
            ClueType.Text -> Toast.makeText(context, "Solving text clue!", Toast.LENGTH_SHORT)
                .show()
            ClueType.Photo -> Toast.makeText(context, "Solving photo clue!", Toast.LENGTH_SHORT)
                .show()
            ClueType.Location -> Toast.makeText(
                context,
                "Solving location clue!",
                Toast.LENGTH_SHORT
            ).show()
            else -> {
            }
        }
}