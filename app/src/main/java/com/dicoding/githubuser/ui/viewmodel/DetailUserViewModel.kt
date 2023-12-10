package com.dicoding.githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.GithubDetailUserResponse
import com.dicoding.githubuser.data.response.repository.FavoriteRepository
import com.dicoding.githubuser.data.response.retrofit.ApiConfig
import com.dicoding.githubuser.data.roomdatabase.Favorite
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    companion object {
        private const val TAG = "DetailUserActivity"
    }

    private val _detailUser = MutableLiveData<GithubDetailUserResponse>()
    val detailUser: LiveData<GithubDetailUserResponse> = _detailUser

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading


    fun setUserDetail(user: String) {
        _showLoading.value = true

        val client = ApiConfig.getApiService().getDetailUser(user)
        client.enqueue(object : Callback<GithubDetailUserResponse> {
            override fun onResponse(
                call: Call<GithubDetailUserResponse>,
                response: Response<GithubDetailUserResponse>
            ) {
                _showLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _detailUser.value = responseBody!!

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubDetailUserResponse>, t: Throwable) {
                _showLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
    fun getFavoriteUser (username: String) = favoriteRepository.isFavoriteUser(username)
    fun insertFavorite (favorite: Favorite) = favoriteRepository.insert(favorite)
    fun delateFavorite (favorite: Favorite) = favoriteRepository.delete(favorite)
}