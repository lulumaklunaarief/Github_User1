package com.dicoding.githubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.data.response.repository.SettingPreferences
import com.dicoding.githubuser.data.response.repository.dataStore
import com.dicoding.githubuser.databinding.ActivityMainBinding
import com.dicoding.githubuser.databinding.ActivityThemeBinding
import com.dicoding.githubuser.ui.adapter.UserAdapter
import com.dicoding.githubuser.ui.viewmodel.ThemeViewModel
import com.dicoding.githubuser.ui.viewmodel.ThemeViewModelFactory
import com.dicoding.githubuser.ui.viewmodel.UserViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userViewModel by viewModels<UserViewModel>()
    private lateinit var bindingTheme: ActivityThemeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingTheme = ActivityThemeBinding.inflate(layoutInflater)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager



        userViewModel.user.observe(this) { user ->
            setUserData(user)
        }
        userViewModel.showLoading.observe(this) {
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    val searchText = searchBar.text.toString()
                    searchView.hide()
                    userViewModel.findUser(searchText)
                    false
                }

            searchBar.setOnMenuItemClickListener{
                when (it.itemId) {
                    R.id.list_favorite -> {
                        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.dark_mode -> {
                        val intent = Intent(this@MainActivity, ThemeActivity::class.java)
                        startActivity(intent)
                        true

                    } else -> {
                        Log.e("error", it.itemId.toString())
                        false

                    }
                }}
        }
        val temaApp = bindingTheme.switchTheme
        theme(temaApp)
    }
    private fun theme(switchMaterial: SwitchMaterial) {
        val preferences = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(preferences))[ThemeViewModel::class.java]
        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchMaterial.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchMaterial.isChecked = false
            }
        }
        switchMaterial.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSettings(isChecked)
        }
    }


    private fun setUserData(userList: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(userList)
        binding.rvUser.adapter = adapter


    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}