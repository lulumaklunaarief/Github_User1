package com.dicoding.githubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
import com.dicoding.githubuser.ui.adapter.UserAdapter
import com.dicoding.githubuser.ui.viewmodel.ThemeViewModel
import com.dicoding.githubuser.ui.viewmodel.ThemeViewModelFactory
import com.dicoding.githubuser.ui.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userViewModel by viewModels<UserViewModel>()
    private lateinit var themeViewModel: ThemeViewModel
    private var isDarkModeActive = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        }

        val preferences = SettingPreferences.getInstance(application.dataStore)
        themeViewModel =
            ViewModelProvider(this, ThemeViewModelFactory(preferences))[ThemeViewModel::class.java]
        themeViewModel.getThemeSettings().observe(this) {
            if (it) {
                isDarkModeActive = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                isDarkModeActive = false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
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

    private fun applyTheme() {
        val mode = if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val themeMenuItem = menu?.findItem(R.id.dark_mode)
        if (isDarkModeActive) {
            themeMenuItem?.setIcon(R.drawable.baseline_mode_night_24)
        } else {
            themeMenuItem?.setIcon(R.drawable.baseline_wb_sunny_24)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_favorites -> {
                val launchFavorite = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(launchFavorite)
            }

            R.id.dark_mode -> {
                isDarkModeActive = !isDarkModeActive
                themeViewModel.saveThemeSettings(isDarkModeActive)
                applyTheme()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}


