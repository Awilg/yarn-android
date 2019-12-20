package com.orienteer.treasurecreate

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceImageLabelerOptions
import com.orienteer.camera.CameraUploadOptionDialog
import com.orienteer.databinding.FragmentCluePhotoCreateBinding
import timber.log.Timber
import java.io.IOException


class ClueTypePhotoCreateFragment : Fragment(),
    CameraUploadOptionDialog.CameraUploadOptionListener {

    lateinit var binding: FragmentCluePhotoCreateBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCluePhotoCreateBinding.inflate(inflater)

        binding.photoClueCreateImg.setOnClickListener {
            val clueDialog = CameraUploadOptionDialog()
            clueDialog.setTargetFragment(this, 0)
            fragmentManager?.let { clueDialog.show(it, "upload_photo_option") }
        }

        return binding.root
    }

    // TODO: move this to be a viewpager
    override fun onUploadResult(uri: Uri) {
        context?.let {
            Glide.with(it)
                .load(uri)
                .into(binding.photoClueCreateImg)
        }
        analyzeImageForTags(uri)
    }

    fun analyzeImageForTags(uri: Uri) {
        var image: FirebaseVisionImage? = null
        try {
            image = FirebaseVisionImage.fromFilePath(context!!, uri)
        } catch (e: IOException) {
            Timber.e("Error creating firebase image from URI $uri with message: ${e.message}")
            e.printStackTrace()
        }

        // Create instance of the firbase labeler
        //val labeler = FirebaseVision.getInstance().onDeviceImageLabeler

        //  Or, to set the minimum confidence required:
        // Switch to cloud image labeler with getCloudImageLabeler()
        val options =
            FirebaseVisionOnDeviceImageLabelerOptions.Builder()
                .setConfidenceThreshold(0.5f)
                .build()
        val labeler = FirebaseVision.getInstance()
            .getOnDeviceImageLabeler(options)

        val tags = StringBuilder()
        image?.let {
            labeler.processImage(image)
                .addOnSuccessListener {
                    // Task completed successfully
                    // ...
                    // TODO: Add these to a recylcerview
                    for (label in it) {
                        tags.append("confidence: ${label.confidence} tag: ${label.text} \n")
                    }

                    binding.tagsTemp.text = tags.toString()
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    // ...
                }
        }

    }
}