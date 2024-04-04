package com.d121211069.submissionandroidfundamental.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d121211069.submissionandroidfundamental.data.remote.response.UserGithubResponse
import com.d121211069.submissionandroidfundamental.data.remote.response.UserItem
import com.d121211069.submissionandroidfundamental.data.remote.retrofit.ApiConfig
import com.d121211069.submissionandroidfundamental.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<UserItem>>()
    val listUser: LiveData<List<UserItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        searchUsers("a")

    }

    fun searchUsers(queryText: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUsers(queryText)
        client.enqueue(object : Callback<UserGithubResponse> {
            override fun onResponse(
                call: Call<UserGithubResponse>, response: Response<UserGithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    _snackbarText.value = Event(response.message())
                }
            }

            override fun onFailure(
                call: Call<UserGithubResponse>, t: Throwable
            ) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
            }
        })
    }
}