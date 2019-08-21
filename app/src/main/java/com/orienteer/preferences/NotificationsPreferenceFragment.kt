package com.orienteer.preferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.orienteer.R

class NotificationsPreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.notification_preferences, rootKey)
    }
}