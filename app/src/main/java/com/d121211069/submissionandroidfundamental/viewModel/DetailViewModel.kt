package com.d121211069.submissionandroidfundamental.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d121211069.submissionandroidfundamental.data.repository.UserRepository
import com.d121211069.submissionandroidfundamental.data.local.entity.UserEntity
import com.d121211069.submissionandroidfundamental.data.remote.response.UserDetailResponse
import com.d121211069.submissionandroidfundamental.data.remote.retrofit.ApiConfig
import com.d121211069.submissionandroidfundamental.util.Event
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<UserDetailResponse>()
    val user: LiveData<UserDetailResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun setUsername(username: String) {
        _username.value = username
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun fetchUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>, response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    _snackbarText.value = Event(response.message())
                }
            }

            override fun onFailure(
                call: Call<UserDetailResponse>, t: Throwable
            ) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
            }
        })
    }

    fun fetchFavoriteUser(user: String) = userRepository.getFavoriteUser(user)

    fun insert() {
        viewModelScope.launch {
            val userEntity = UserEntity(
                login = _user.value?.login.toString(), avatarUrl = _user.value?.avatarUrl
            )
            userRepository.insert(userEntity)
        }
    }

    fun delete() {
        viewModelScope.launch {
            val username = _username.value.toString()
            userRepository.delete(username)
        }
    }
}
