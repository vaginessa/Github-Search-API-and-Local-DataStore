package com.zulfikar.githubuserssearch.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zulfikar.githubuserssearch.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM `Github User` ORDER BY username ASC")
    fun getUser(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM `Github User` where bookmarked = 1")
    fun getBookmarkedUser(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM `Github User` WHERE username = :username")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: List<UserEntity>)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM `Github User` WHERE bookmarked = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM `Github User` WHERE username = :username AND bookmarked = 1)")
    suspend fun isUserBookmarked(username: String): Boolean
}