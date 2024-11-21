package com.example.dicodingaplikasi.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dicodingaplikasi.R
import com.example.dicodingaplikasi.databinding.ActivityMainBinding
import com.example.dicodingaplikasi.ui.setting.SettingPreferences
import com.example.dicodingaplikasi.ui.setting.SettingViewModel
import com.example.dicodingaplikasi.ui.setting.SettingViewModelFactory
import com.example.dicodingaplikasi.ui.setting.dataStore

class MainActivity : AppCompatActivity() {

private lateinit var binding: ActivityMainBinding


override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val pref = SettingPreferences.getInstance(this.dataStore)
    val settingViewModel = ViewModelProvider(
        this,
        SettingViewModelFactory(pref)
    )[SettingViewModel::class.java]
    settingViewModel.getThemeSetting().observe(this) { isDarkModeActive ->
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_upcoming, R.id.navigation_finished, R.id.navigation_favorite, R.id.navigation_settings
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


}