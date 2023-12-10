package com.dicoding.githubuser.data.response.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.githubuser.data.roomdatabase.Favorite
import com.dicoding.githubuser.data.roomdatabase.FavoriteDao
import com.dicoding.githubuser.data.roomdatabase.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDoa: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDoa = db.FavoriteDao()
    }

    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteDoa.getAllFavorite()
    fun isFavoriteUser(username: String): LiveData<String> = mFavoriteDoa.isFavoriteUser(username)

    fun insert(user: Favorite) {
        executorService.execute { mFavoriteDoa.insert(user)}
    }

    fun delete(user: Favorite) {
        executorService.execute { mFavoriteDoa.delete(user) }
    }

}