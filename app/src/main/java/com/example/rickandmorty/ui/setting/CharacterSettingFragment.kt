package com.example.rickandmorty.ui.setting

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.rickandmorty.R


class MySettingsFragment : PreferenceFragmentCompat() {

    companion object {
        const val KEY_PREF_EXAMPLE_SWITCH = "notifications"
    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_rick_and_morty, rootKey)
    }
}