package com.d121211069.submissionandroidfundamental.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.d121211069.submissionandroidfundamental.data.repository.UserRepository
import com.d121211069.submissionandroidfundamental.di.Injection
import com.d121211069.submissionandroidfundamental.viewModel.DetailViewModel
import com.d121211069.submissionandroidfundamental.viewModel.FavoriteViewModel

class ViewModelFactory private constructor(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideRepository(context))
        }.also { instance = it }
    }
}