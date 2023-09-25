package com.zulfikar.githubuserssearch.di

import android.content.Context
import com.zulfikar.githubuserssearch.data.UserRepository
import com.zulfikar.githubuserssearch.data.local.room.UserDatabase
import com.zulfikar.githubuserssearch.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(apiService, dao)
    }
}