package com.orienteer.treasurecreate

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.orienteer.camera.CameraUploadOptionDialog
import com.orienteer.databinding.FragmentCluePhotoCreateBinding

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
    }
}