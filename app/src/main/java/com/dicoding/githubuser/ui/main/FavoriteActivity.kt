package com.dicoding.githubuser.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.databinding.ActivityFavoriteBinding
import com.dicoding.githubuser.ui.adapter.FavoriteAdapter
import com.dicoding.githubuser.ui.viewmodel.FavoriteUserViewModel
import com.dicoding.githubuser.ui.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteUserViewModel by viewModels<FavoriteUserViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val item = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(item)

        val adapter = FavoriteAdapter()
        binding.rvFavorite.adapter = adapter
        favoriteUserViewModel.getAllFavorite().observe(this){
            adapter.submitList(it)
        }
    }
}