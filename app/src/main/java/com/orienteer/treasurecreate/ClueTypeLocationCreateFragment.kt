package com.orienteer.treasurecreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.orienteer.databinding.FragmentClueLocationCreateBinding

class ClueTypeLocationCreateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentClueLocationCreateBinding.inflate(inflater)
        return binding.root
    }
}