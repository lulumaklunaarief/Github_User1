package com.dicoding.githubuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.repository.FavoriteRepository

class FavoriteUserViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    fun getAllFavorite() = favoriteRepository.getAllFavorite()
}