package com.orienteer.preferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.orienteer.R

class PreferencesFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.user_preferences, rootKey)
    }
}