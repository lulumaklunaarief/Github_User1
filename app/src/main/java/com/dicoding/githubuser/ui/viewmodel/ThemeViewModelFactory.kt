package com.dicoding.githubuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.data.response.repository.SettingPreferences

class ThemeViewModelFactory(private val preference: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelclass: Class<T>) : T {
        if (modelclass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(preference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class:" + modelclass.name)
    }
}