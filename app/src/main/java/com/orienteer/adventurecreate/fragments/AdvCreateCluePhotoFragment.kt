package com.orienteer.adventurecreate.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.ext.IMAGE_PICKER_REQUEST_CODE
import com.orienteer.adventurecreate.ext.getBitmapsFromResultData
import com.orienteer.adventurecreate.ext.openImageSaf
import com.orienteer.adventurecreate.models.AdvCreateImgPreview_
import com.orienteer.adventurecreate.models.advCreateInputRow
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.buttonOutlined
import com.orienteer.createSectionHeader
import com.orienteer.databinding.FragmentAdvcreateCluesLocationBinding
import com.orienteer.label
import com.orienteer.models.ClueType
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.carousel
import com.orienteer.util.simpleController
import com.orienteer.util.withModelsFrom

class AdvCreateCluePhotoFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()
    lateinit var binding: FragmentAdvcreateCluesLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdvcreateCluesLocationBinding.inflate(inflater)
        recyclerView = binding.titleinfoRecyclerView
        recyclerView.setController(epoxyController)
        recyclerView.setItemSpacingDp(16)
        binding.actionButtonSection.detailActiveButton.setOnClickListener {
            viewModel.savePhotoClue()
            this.findNavController().navigate(
                AdvCreateCluePhotoFragmentDirections.actionAdvCreateCluePhotoFragmentToAdvCreateCluesSummaryFragment()
            )
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)

        if (requestCode == IMAGE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            viewModel.updateCurrentPhotoCluePhotos(getBitmapsFromResultData(resultData))
        }
    }

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

        label {
            id("id_section_photo_solution_definition")
            text("It's best to add photos where the subject is centered, unobstructed, and well lit")
        }

        carousel {
            id("carousel")

            withModelsFrom(state.currentPhotoCluePhotos) {
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