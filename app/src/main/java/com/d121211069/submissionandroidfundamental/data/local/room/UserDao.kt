package com.d121211069.submissionandroidfundamental.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.d121211069.submissionandroidfundamental.data.local.entity.UserEntity
import com.d121211069.submissionandroidfundamental.data.remote.response.UserItem

@Dao
interface UserDao {
    @Query("SELECT * FROM favorite_users")
    fun getAllFavoriteUsers(): LiveData<List<UserItem>>

    @Query("SELECT * FROM favorite_users WHERE login = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<UserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteUser(user: UserEntity)

    @Query("DELETE FROM favorite_users WHERE login = :username")
    suspend fun deleteFavoriteUser(username: String)
}