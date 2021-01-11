package com.orienteer.adventurecreate.fragments

import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.ext.openImageSaf
import com.orienteer.adventurecreate.models.AdvCreateImgPreview_
import com.orienteer.adventurecreate.models.advCreateInputRow
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.buttonOutlined
import com.orienteer.createSectionHeader
import com.orienteer.models.ClueType
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.carousel
import com.orienteer.util.simpleController
import com.orienteer.util.withModelsFrom

class AdvCreateCluePhotoFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()


    override fun epoxyController() = simpleController(viewModel) { state ->
        createSectionHeader {
            id("id_section_photo_clue")
            header("Photo Clue Editor")
            subtitle("Photo clues consist of a clue prompt, photos of the solution (a landmark, object, famous person, etc.) and some optional hints.")
        }

        advCreateInputRow {
            id("id_clue_prompt")
            text(state.currentPhotoCluePrompt)
            prompt("Clue Prompt")
            hint("e.g. This International Orange Icon connects San Francisco and Marin County")
            onEditTextChanged { viewModel.updateCluePrompt(it, ClueType.Photo) }
        }

        //TODO: make a better model for this
        createSectionHeader {
            id("id_section_photo_solution_definition")
            header("Clue Solution")
            subtitle("It's best to add photos where the subject is centered, unobstructed, and well lit")
        }

        carousel {
            id("carousel")

            withModelsFrom(state.photos) {
                AdvCreateImgPreview_()
                    .id(it.generationId.toString())
                    .bitmap(it)
            }
        }

        buttonOutlined {
            id("id_add_photos_button")
            buttonText("Upload")
            onClickListener { _ ->
                openImageSaf()
            }
        }
    }
}