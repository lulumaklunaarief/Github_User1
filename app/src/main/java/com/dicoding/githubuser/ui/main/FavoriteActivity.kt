package com.dicoding.githubuser.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.databinding.ActivityFavoriteBinding
import com.dicoding.githubuser.ui.adapter.FavoriteAdapter
import com.dicoding.githubuser.ui.viewmodel.FavoriteUserViewModel
import com.dicoding.githubuser.ui.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        favoriteUserViewModel = ViewModelProvider(this, ViewModelFactory(application))[FavoriteUserViewModel::class.java]


        favoriteUserViewModel.getAllFavorite().observe(this) {
            binding.apply {
                rvFavorite.adapter = FavoriteAdapter(it)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }




}


