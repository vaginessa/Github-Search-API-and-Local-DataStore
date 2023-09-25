package com.zulfikar.githubuserssearch.data.remote.response

import com.google.gson.annotations.SerializedName

data class FollowItem(

    @field:SerializedName("login") val login: String,
    @field:SerializedName("avatar_url") val avatarUrl: String,

    )