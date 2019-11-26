package com.ricode.kotlinwords.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.ricode.kotlinwords.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        setPreferencesFromResource(R.xml.app_preferences, rootKey)
    }


}
