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
    fun insert(user: Favorite)
    @Delete
    fun delete(user: Favorite)
    @Query("SELECT username FROM Favorite WHERE username= :username")
    fun isFavoriteUser(username: String): LiveData<String>

    @Query("SELECT * FROM Favorite ORDER BY username ASC" )
    fun getAllFavorite(): LiveData<List<Favorite>>
}