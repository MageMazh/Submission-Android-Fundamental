package com.d121211069.submissionandroidfundamental.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_users")
@Parcelize
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    var login: String = "",
    var avatarUrl: String? = null,
) : Parcelable