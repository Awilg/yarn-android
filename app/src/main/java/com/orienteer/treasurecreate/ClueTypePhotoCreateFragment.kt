package com.orienteer.treasurecreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.orienteer.databinding.FragmentCluePhotoCreateBinding
import timber.log.Timber

class ClueTypePhotoCreateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("Creating fragment view!")
        val binding = FragmentCluePhotoCreateBinding.inflate(inflater)

        binding.photoClueCreateImg.setOnClickListener {
            Toast.makeText(context, "Clicked the edit button!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}