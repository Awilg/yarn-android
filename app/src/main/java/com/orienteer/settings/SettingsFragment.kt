package com.orienteer.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.orienteer.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.user_settings, rootKey)
    }

}