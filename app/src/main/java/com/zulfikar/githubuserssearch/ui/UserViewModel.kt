package com.zulfikar.githubuserssearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zulfikar.githubuserssearch.data.UserRepository
import com.zulfikar.githubuserssearch.data.local.entity.UserEntity
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel(){
    fun getUser(username: String) = userRepository.getUser(username)

    fun getBookmarkedUser() = userRepository.getBookmarkedUser()

    fun saveUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.setUserBookmark(user, true)
        }
    }

    fun deleteUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.setUserBookmark(user, false)
        }
    }

    fun getDetail(username: String) = userRepository.getDetail(username)
    fun getFollowing(username: String) = userRepository.getFollowing(username)
    fun getFollower(username: String) = userRepository.getFollower(username)
}