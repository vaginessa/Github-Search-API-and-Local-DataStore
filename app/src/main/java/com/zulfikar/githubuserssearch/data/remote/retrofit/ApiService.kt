package com.zulfikar.githubuserssearch.data.remote.retrofit

import com.zulfikar.githubuserssearch.data.remote.response.DetailUserResponse
import com.zulfikar.githubuserssearch.data.remote.response.FollowItem
import com.zulfikar.githubuserssearch.data.remote.response.SearchUserResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getUser(
        @Query("q") username: String, @Header("Authorization") token: String
    ): SearchUserResponse

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String, @Header("Authorization") token: String
    ): DetailUserResponse

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username: String, @Header("Authorization") token: String
    ): List<FollowItem>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String, @Header("Authorization") token: String
    ): List<FollowItem>
}