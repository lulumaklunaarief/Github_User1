package com.dicoding.githubuser.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.response.repository.SettingPreferences
import com.dicoding.githubuser.data.response.repository.dataStore
import com.dicoding.githubuser.ui.viewmodel.ThemeViewModel
import com.dicoding.githubuser.ui.viewmodel.ThemeViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

class ThemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)
        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val preferences = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(preferences)).get(ThemeViewModel::class.java)
        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                Log.d("tab", "")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSettings(isChecked)
        }
    }
}