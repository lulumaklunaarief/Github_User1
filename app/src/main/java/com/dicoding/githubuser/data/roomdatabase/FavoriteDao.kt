package com.dicoding.githubuser.data.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * from Favorite ORDER BY username ASC")
    fun getAllFavorite(): LiveData<List<Favorite>>

    @Query("SELECT * from Favorite WHERE username = :username")
    fun isFavoriteUser(username:String): LiveData<Favorite>
}