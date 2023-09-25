package com.zulfikar.githubuserssearch.data

import com.zulfikar.githubuserssearch.data.local.room.UserDao
import com.zulfikar.githubuserssearch.data.remote.retrofit.ApiService
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.zulfikar.githubuserssearch.data.local.entity.DetailUserEntity
import com.zulfikar.githubuserssearch.data.local.entity.UserEntity


class UserRepository private constructor(
    private val apiService: ApiService, private val userDao: UserDao
) {
    private val token = "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"
    fun getUser(username: String): LiveData<Result<List<UserEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUser(username, token)
            val user = response.items
            val userList = user.map { user ->
                val isBookmarked = userDao.isUserBookmarked(user.login)
                UserEntity(
                    user.login, user.avatarUrl, isBookmarked
                )
            }
            userDao.deleteAll()
            userDao.insertUser(userList)
        } catch (e: Exception) {
            Log.d("UserRepository", "getUser: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<UserEntity>>> =
            userDao.getUser().map { Result.Success(it) }
        emitSource(localData)
    }

    fun getDetail(username: String): LiveData<Result<DetailUserEntity>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserDetail(username, token)
            val user = DetailUserEntity(
                response.login,
                response.avatarUrl,
                response.followingUrl,
                response.followersUrl,
                response.followers,
                response.following,
                response.name,
                false
            )
            emit(Result.Success(user))
        } catch (e: Exception) {
            Log.d("UserRepository", "getDetail: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowing(username: String): LiveData<Result<List<UserEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserFollowing(username, token)
            val user = response
            val followingList = user.map { user ->
                val isBookmarked = userDao.isUserBookmarked(user.login)
                UserEntity(
                    user.login, user.avatarUrl, isBookmarked
                )
            }
            emit(Result.Success(followingList))
        } catch (e: Exception) {
            Log.d("UserRepository", "getFollowing: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollower(username: String): LiveData<Result<List<UserEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserFollowers(username, token)
            val user = response
            val followingList = user.map { user ->
                val isBookmarked = userDao.isUserBookmarked(user.login)
                UserEntity(
                    user.login, user.avatarUrl, isBookmarked
                )
            }
            emit(Result.Success(followingList))
        } catch (e: Exception) {
            Log.d("UserRepository", "getFollowing: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getBookmarkedUser(): LiveData<List<UserEntity>> {
        return userDao.getBookmarkedUser()
    }

    suspend fun setUserBookmark(user: UserEntity, bookmarkState: Boolean) {
        val existingUser = userDao.getUserByUsername(user.username)

        if (existingUser != null) {
            existingUser.isBookmarked = bookmarkState
            userDao.updateUser(existingUser)
        } else {
            val newUser = UserEntity(
                username = user.username,
                avatarUrl = user.avatarUrl,
                isBookmarked = bookmarkState
            )
            userDao.insertUser(listOf(newUser))
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService, userDao: UserDao
        ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(apiService, userDao)
        }.also { instance = it }
    }

}