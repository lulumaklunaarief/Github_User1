package com.dicoding.githubuser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.repository.FavoriteRepository
import com.dicoding.githubuser.data.roomdatabase.Favorite

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    fun getAllFavorite() : LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()

    fun isFavoriteUser(username: String): LiveData<String> = mFavoriteRepository.isFavoriteUser(username)

    fun insert(user: Favorite) {
        mFavoriteRepository.insert(user)
    }
    fun delete(user: Favorite) {
        mFavoriteRepository.delete(user)
    }
}