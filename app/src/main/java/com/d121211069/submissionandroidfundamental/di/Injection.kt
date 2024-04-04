package com.d121211069.submissionandroidfundamental.di

import android.content.Context
import com.d121211069.submissionandroidfundamental.data.repository.UserRepository
import com.d121211069.submissionandroidfundamental.data.local.room.UserRoomDatabase

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = UserRoomDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(dao)
    }
}