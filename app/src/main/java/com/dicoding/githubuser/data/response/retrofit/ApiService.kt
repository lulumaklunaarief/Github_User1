package com.dicoding.githubuser.data.response.retrofit

import com.dicoding.githubuser.data.response.GithubDetailUserResponse
import com.dicoding.githubuser.data.response.GithubUserResponse
import com.dicoding.githubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getEveryUsers() : Call<List<ItemsItem>>
    @GET("search/users")
    fun getUsers(
        @Query("q") username: String
    ): Call<GithubUserResponse>
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username")
        username: String
    ): Call<GithubDetailUserResponse>
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username")
        username: String
    ): Call<List<ItemsItem>>
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username")
        username: String
    ): Call<List<ItemsItem>>
}