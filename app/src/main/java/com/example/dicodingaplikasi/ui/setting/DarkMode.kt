package com.example.dicodingaplikasi.ui.setting

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import com.example.dicodingaplikasi.R
import com.google.android.material.switchmaterial.SwitchMaterial


class DarkMode : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dark_mode, container, false)

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        // Find SwitchMaterial view from the layout
        val switchTheme = view.findViewById<SwitchMaterial>(R.id.switch_theme)

        // Check the current theme mode from SharedPreferences and set the switch state
        switchTheme.isChecked = sharedPreferences.getBoolean("DARK_MODE", false)

        // Listener for switch state changes
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            // Save the preference and apply the theme
            val editor = sharedPreferences.edit()
            editor.putBoolean("DARK_MODE", isChecked)
            editor.apply()

            // Apply theme mode
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        return view
    }
}

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//
//    }
