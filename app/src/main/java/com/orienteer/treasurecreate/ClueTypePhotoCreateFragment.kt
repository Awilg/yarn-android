package com.orienteer.treasurecreate

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.orienteer.camera.CameraUploadOptionDialog
import com.orienteer.databinding.FragmentCluePhotoCreateBinding
import com.orienteer.util.hideKeyboard
import timber.log.Timber
import java.io.IOException


class ClueTypePhotoCreateFragment : Fragment(),
    CameraUploadOptionDialog.CameraUploadOptionListener {

    private lateinit var viewModel: TreasureCreateViewModel
    lateinit var binding: FragmentCluePhotoCreateBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCluePhotoCreateBinding.inflate(inflater)

        // Grab the viewmodel for the treasure create fragment
        viewModel = activity?.run {
            ViewModelProviders.of(this)[TreasureCreateViewModel::class.java]
        }!!

        binding.photoClueCreateImg.setOnClickListener {
            val clueDialog = CameraUploadOptionDialog()
            clueDialog.setTargetFragment(this, 0)
            fragmentManager?.let { clueDialog.show(it, "upload_photo_option") }
        }

//        binding.createButton.clueCreateButton.setOnClickListener {
//            hideKeyboard()
//            createPhotoClue()
//            findNavController().navigate(
//                ClueTypePhotoCreateFragmentDirections.actionClueTypePhotoCreateFragmentToTreasureCreateFragment()
//            )
//        }

        return binding.root
    }

    private fun createPhotoClue() {
        // Get the prompt
        val prompt = binding.photoClueCreateHint.text.toString()
        val currClue = viewModel.currentPhotoClue.value
        currClue?.let {
            viewModel.addPhotoClue(prompt)
        }
    }

    // TODO: move this to be a viewpager
    override fun onUploadResult(uri: Uri) {
        context?.let {
            Glide.with(it)
                .load(uri)
                .into(binding.photoClueCreateImg)
        }
        viewModel.setCurrentPhotoClueUri(uri)
        analyzeImageForTags(uri)
    }

    private fun analyzeImageForTags(uri: Uri) {
//        var image: FirebaseVisionImage? = null
//        try {
//            image = FirebaseVisionImage.fromFilePath(requireContext(), uri)
//        } catch (e: IOException) {
//            Timber.e("Error creating firebase image from URI $uri with message: ${e.message}")
//            e.printStackTrace()
//        }
//
//        // Create instance of the firbase labeler
//        //val labeler = FirebaseVision.getInstance().onDeviceImageLabeler
//
//        //  Or, to set the minimum confidence required:
//        // Switch to cloud image labeler with getCloudImageLabeler()
//        val options =
//            FirebaseVisionCloudImageLabelerOptions.Builder()
//                .setConfidenceThreshold(0.3f)
//                .build()
//        val labeler = FirebaseVision.getInstance()
//            .getCloudImageLabeler(options)
//
//        val tags = StringBuilder()
//        image?.let {
//            labeler.processImage(image)
//                .addOnSuccessListener {
//                    // Task completed successfully
//                    // TODO: Add these to a recylcerview
//                    for (label in it) {
//                        tags.append("confidence: ${label.confidence} tag: ${label.text} \n")
//                    }
//
//                    binding.tagsTemp.text = tags.toString()
//                }
//                .addOnFailureListener {
//                    // Task failed with an exception
//                    // ...
//                }
//        }

    }
}