package com.orienteer.adventurecreate.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.ext.IMAGE_PICKER_REQUEST_CODE
import com.orienteer.adventurecreate.ext.getBitmapsFromResultData
import com.orienteer.adventurecreate.ext.openImageSaf
import com.orienteer.adventurecreate.models.AdvCreateImgPreview_
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.buttonOutlined
import com.orienteer.createSectionHeader
import com.orienteer.databinding.FragmentAdvcreatePhotosBinding
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.carousel
import com.orienteer.util.simpleController
import com.orienteer.util.withModelsFrom


const val IMAGE_MIME_TYPE = "image/*"

class AdvCreatePhotoFragment : MavericksBaseFragment() {

    private val viewModel: AdvCreateSummaryViewModel by activityViewModel()
    lateinit var binding: FragmentAdvcreatePhotosBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdvcreatePhotosBinding.inflate(inflater)
        recyclerView = binding.photosRecyclerView
        recyclerView.setController(epoxyController)

        binding.actionButtonSection.detailActiveButton.setOnClickListener {
            //TODO: save the in progress adventure
            viewModel.savePhotos()
            this.findNavController().navigate(
                AdvCreatePhotoFragmentDirections.actionAdvCreatePhotoFragmentToAdvCreatePublishingFragment()
            )
        }
        return binding.root
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        createSectionHeader {
            id("id_title_section")
            header("Add some photos")
            subtitle("Draw people in with a taste of what they might see on your adventure")
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
            buttonText("Add photos")
            onClickListener { _ ->
                Toast.makeText(context, "photo gallery", Toast.LENGTH_SHORT).show()
                openImageSaf()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)

        if (requestCode == IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            viewModel.updatePhotos(getBitmapsFromResultData(resultData))
        }
    }
}