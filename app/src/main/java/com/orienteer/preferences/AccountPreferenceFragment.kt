package com.orienteer.preferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.orienteer.R

class AccountPreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.account_preferences, rootKey)
    }
}