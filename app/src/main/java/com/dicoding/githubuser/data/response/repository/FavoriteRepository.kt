package com.dicoding.githubuser.data.response.repository

import androidx.lifecycle.LiveData
import com.dicoding.githubuser.data.roomdatabase.Favorite
import com.dicoding.githubuser.data.roomdatabase.FavoriteDao
import com.dicoding.githubuser.ui.viewmodel.AppExecutors

class FavoriteRepository private constructor (
    private val mFavoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors,
) {
    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorite()
    fun isFavoriteUser(username: String) : LiveData<Favorite> {
        return mFavoriteDao.isFavoriteUser(username)
    }
    fun insert(favorite: Favorite) {
        appExecutors.diskIO.execute { mFavoriteDao.insert(favorite)}
    }
    fun delete(favorite: Favorite) {
        appExecutors.diskIO.execute { mFavoriteDao.delete(favorite)}
    }

    companion object {
        @Volatile
        private var instance:FavoriteRepository? = null
        fun getInstance(favoriteDao: FavoriteDao, appExecutors: AppExecutors): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(favoriteDao, appExecutors)
            }.also { instance = it }
    }

}