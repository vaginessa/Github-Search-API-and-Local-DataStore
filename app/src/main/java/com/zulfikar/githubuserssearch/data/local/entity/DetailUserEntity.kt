package com.zulfikar.githubuserssearch.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class DetailUserEntity(
    @field:ColumnInfo(name = "username") @field:PrimaryKey var username: String,
    @field:ColumnInfo(name = "avatarUrl") var avatarUrl: String,
    @field:ColumnInfo(name = "following_url") var followingUrl: String,
    @field:ColumnInfo(name = "followers_url") var followersUrl: String,
    @field:ColumnInfo(name = "followers") var followers: Int,
    @field:ColumnInfo(name = "following") var following: Int,
    @field:ColumnInfo(name = "name") var name: String,
    @field:ColumnInfo(name = "bookmarked") var isBookmarked: Boolean
)