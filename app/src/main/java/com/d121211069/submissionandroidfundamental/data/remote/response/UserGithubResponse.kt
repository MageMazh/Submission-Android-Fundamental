package com.d121211069.submissionandroidfundamental.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserGithubResponse(
    @field:SerializedName("total_count")
    val totalCount: Int? = null,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,

    @field:SerializedName("items")
    val items: List<UserItem>
)

data class UserItem(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,
)
