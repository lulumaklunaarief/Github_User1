package com.dicoding.githubuser.data.response.injection

import android.content.Context
import com.dicoding.githubuser.data.response.repository.FavoriteRepository
import com.dicoding.githubuser.data.roomdatabase.FavoriteRoomDatabase
import com.dicoding.githubuser.ui.viewmodel.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val database = FavoriteRoomDatabase.getDatabase(context)
        val dao = database.FavoriteDao()
        val appExecutors  = AppExecutors()
        return FavoriteRepository.getInstance(dao, appExecutors)
    }
}