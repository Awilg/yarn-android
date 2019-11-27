package com.orienteer.preferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.orienteer.R

class AppearancePreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.appearance_preferences, rootKey)
    }
}