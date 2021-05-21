package com.example.githubuserapp.networks

import androidx.lifecycle.liveData
import com.example.githubuserapp.networks.api.Retrofit
import com.example.githubuserapp.networks.local.GithubUserDao
import com.example.githubuserapp.utils.Resource
import kotlinx.coroutines.Dispatchers

object GithubRepository {
    fun searchUsers(query: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val userSearch = Retrofit.apiClient.getSearchUser(query)
            emit(Resource.success(userSearch.items))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error"))
        }
    }

    fun getDetailUser(username: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(Retrofit.apiClient.getSearchByUserName(username)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error"))
        }
    }

    fun getFollowers(username: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(Retrofit.apiClient.getFollowers(username)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error"))
        }
    }

    fun getFollowing(username: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(Retrofit.apiClient.getFollowing(username)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error"))
        }
    }

    fun getFavorite(githubUserDao: GithubUserDao) = githubUserDao.getUsers()
}