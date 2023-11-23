package com.dicoding.githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.GithubDetailUserResponse
import com.dicoding.githubuser.data.response.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {

    companion object {
        private const val TAG = "DetailActivity"
    }

    private val _detailUser = MutableLiveData<GithubDetailUserResponse>()
    val detailUser: LiveData<GithubDetailUserResponse> = _detailUser

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading


    fun setUserDetail(username: String) {
        _showLoading.value = true

        val client = ApiConfig.getApiService().getDetailUser(username)
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
}