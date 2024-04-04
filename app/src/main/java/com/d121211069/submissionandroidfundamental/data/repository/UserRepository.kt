package com.d121211069.submissionandroidfundamental.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.d121211069.submissionandroidfundamental.data.local.entity.UserEntity
import com.d121211069.submissionandroidfundamental.data.local.room.UserDao
import com.d121211069.submissionandroidfundamental.data.remote.response.UserItem
import com.d121211069.submissionandroidfundamental.util.Result

class UserRepository private constructor(
    private val userDao: UserDao,
) {
    fun getAllFavoriteUsers(): LiveData<Result<List<UserItem>>> = liveData {
        emit(Result.Loading)
        try {
            val data = userDao.getAllFavoriteUsers()
            val localData: LiveData<Result<List<UserItem>>> = data.map { Result.Success(it) }
            emitSource(localData)

        } catch (e: Exception) {
            Log.d("UserRepository", "getHeadlineUser: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFavoriteUser(username: String): LiveData<UserEntity> {
        return userDao.getFavoriteUserByUsername(username)
    }

    suspend fun insert(user: UserEntity) {
        userDao.insertFavoriteUser(user)
    }

    suspend fun delete(username: String) {
        userDao.deleteFavoriteUser(username)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userDao: UserDao,
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userDao)
            }.also { instance = it }
    }
}