package com.d121211069.submissionandroidfundamental.viewModel

import androidx.lifecycle.ViewModel
import com.d121211069.submissionandroidfundamental.data.repository.UserRepository

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getAllFavoriteUsers() = userRepository.getAllFavoriteUsers()
}