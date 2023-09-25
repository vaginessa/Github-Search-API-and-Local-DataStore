package com.zulfikar.githubuserssearch.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
    @field:SerializedName("items") val items: List<Item>
)

data class Item(

    @field:SerializedName("login") val login: String,

    @field:SerializedName("avatar_url") val avatarUrl: String,
)