package com.zulfikar.githubuserssearch.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Github User")
class UserEntity(
    @field:ColumnInfo(name = "username") @field:PrimaryKey var username: String,
    @field:ColumnInfo(name = "avatarUrl") var avatarUrl: String,
    @field:ColumnInfo(name = "bookmarked") var isBookmarked: Boolean
)