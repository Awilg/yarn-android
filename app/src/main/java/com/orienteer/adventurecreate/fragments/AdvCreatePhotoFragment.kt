package com.orienteer.adventurecreate.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.mvrx.activityViewModel
import com.orienteer.adventurecreate.models.AdvCreateImgPreview_
import com.orienteer.adventurecreate.models.gridCarousel
import com.orienteer.adventurecreate.viewmodel.AdvCreateSummaryViewModel
import com.orienteer.buttonOutlined
import com.orienteer.createSectionHeader
import com.orienteer.databinding.FragmentAdvcreatePhotosBinding
import com.orienteer.util.MavericksBaseFragment
import com.orienteer.util.simpleController


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
        val spanCount = 2
        val layoutManager = GridLayoutManager(context, spanCount)
        epoxyController.spanCount = spanCount
        layoutManager.spanSizeLookup = epoxyController.spanSizeLookup
        return binding.root
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        createSectionHeader {
            id("id_title_section")
            header("Add some photos")
            subtitle("Draw people in with a taste of what they might see on your adventure")
        }

        val photoModels: MutableList<AdvCreateImgPreview_> = arrayListOf()
        state.photos.forEach {
            photoModels.add(
                AdvCreateImgPreview_()
                    .id(it.generationId.toString())
                    .bitmap(it)
            )
        }

        gridCarousel {
            id("grid_carousel")
            models(photoModels)
        }


        buttonOutlined {
            id("id_add_photos_button")
            buttonText("Add photos")
            onClickListener { _ ->
                Toast.makeText(context, "photo gallery", Toast.LENGTH_SHORT).show()
                selectImage()
            }
        }
    }

    // Request code for selecting a PDF document.
    val IMAGE_PICKER_REQUEST_CODE = 2

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = IMAGE_MIME_TYPE
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }

        startActivityForResult(intent, IMAGE_PICKER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)

        if (requestCode == IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {

            if (resultData?.clipData != null) {
                resultData.clipData?.let { uris ->
                    activity?.let { fragActivity ->
                        val selectedUris: MutableList<Uri> = arrayListOf()
                        for (index in 0 until uris.itemCount) {
                            selectedUris.add(uris.getItemAt(index).uri)
                        }

                        val decodedBitmaps: MutableList<Bitmap> = arrayListOf()
                        selectedUris.forEach { uri ->
                            decodedBitmaps.add(
                                BitmapFactory.decodeStream(
                                    fragActivity.contentResolver?.openInputStream(
                                        uri
                                    )
                                )
                            )
                        }

                        viewModel.updatePhotos(decodedBitmaps)
                    }
                }
            } else {
                resultData?.data?.let { uri ->
                    activity?.let { fragActivity ->
                        viewModel.updatePhotos(
                            listOf(
                                BitmapFactory.decodeStream(
                                    fragActivity.contentResolver?.openInputStream(
                                        uri
                                    )
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}