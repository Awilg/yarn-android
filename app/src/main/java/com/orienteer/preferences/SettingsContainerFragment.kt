package com.orienteer.preferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.orienteer.R
import com.orienteer.databinding.FragmentSettingsContainerBinding

class SettingsContainerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSettingsContainerBinding.inflate(inflater)

        if (savedInstanceState == null) {
            fragmentManager?.beginTransaction()?.replace(R.id.settings, PreferencesFragment())?.commit()
        }

        return binding.root
    }
}