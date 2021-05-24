package com.example.githubuserapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.githubuserapp.data.api.Retrofit
import com.example.githubuserapp.data.local.GithubUserDao
import com.example.githubuserapp.models.UserGithub
import com.example.githubuserapp.utils.Resource
import kotlinx.coroutines.Dispatchers

object GithubRepository {
    fun searchUsers(query: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val userSearch = Retrofit.apiClient.searchUsers(query)
            emit(Resource.success(userSearch.items))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message ?: "Error Detected: ${e.localizedMessage}"))
        }
    }

    fun getFollowers(username: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(Retrofit.apiClient.userFollower(username)))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message ?: "Error Detected: ${e.localizedMessage}"))
        }
    }

    fun getFollowing(username: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(Retrofit.apiClient.userFollowing(username)))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message ?: "Error Detected: ${e.localizedMessage}"))
        }
    }

    fun getFavoriteUsers(userDao: GithubUserDao): LiveData<List<UserGithub>> = userDao.getUserList()
}