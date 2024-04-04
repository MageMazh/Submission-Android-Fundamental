package com.d121211069.submissionandroidfundamental.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d121211069.submissionandroidfundamental.data.remote.response.UserFollowResponseItem
import com.d121211069.submissionandroidfundamental.data.remote.retrofit.ApiConfig
import com.d121211069.submissionandroidfundamental.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val _listUserFollower = MutableLiveData<List<UserFollowResponseItem>>()
    val listUserFollower: LiveData<List<UserFollowResponseItem>> = _listUserFollower

    private val _listUserFollowing = MutableLiveData<List<UserFollowResponseItem>>()
    val listUserFollowing: LiveData<List<UserFollowResponseItem>> = _listUserFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _username = MutableLiveData<String?>()
    val username: LiveData<String?> = _username

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun setUsername(username: String?) {
        _username.value = username
    }

    companion object {
        private const val TAG = "FollowViewModel"
    }

    fun fetchUserFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<List<UserFollowResponseItem>> {
            override fun onResponse(
                call: Call<List<UserFollowResponseItem>>,
                response: Response<List<UserFollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {

                    _listUserFollowing.value = response.body()
                } else {
                    _snackbarText.value = Event(response.message())
                }
            }

            override fun onFailure(
                call: Call<List<UserFollowResponseItem>>, t: Throwable
            ) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
            }
        })
    }

    fun fetchUserFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<UserFollowResponseItem>> {
            override fun onResponse(
                call: Call<List<UserFollowResponseItem>>,
                response: Response<List<UserFollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {

                    _listUserFollower.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(
                call: Call<List<UserFollowResponseItem>>, t: Throwable
            ) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}