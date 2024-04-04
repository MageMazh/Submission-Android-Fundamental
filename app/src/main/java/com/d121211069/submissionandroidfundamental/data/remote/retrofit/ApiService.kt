package com.d121211069.submissionandroidfundamental.data.remote.retrofit

import com.d121211069.submissionandroidfundamental.data.remote.response.UserDetailResponse
import com.d121211069.submissionandroidfundamental.data.remote.response.UserFollowResponseItem
import com.d121211069.submissionandroidfundamental.data.remote.response.UserGithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserGithubResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<UserFollowResponseItem>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<UserFollowResponseItem>>
}