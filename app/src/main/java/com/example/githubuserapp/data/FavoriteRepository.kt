package com.example.githubuserapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.githubuserapp.data.api.Retrofit
import com.example.githubuserapp.data.local.GithubUserDao
import com.example.githubuserapp.models.UserGithub
import com.example.githubuserapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class FavoriteRepository(private val githubUserDao: GithubUserDao) {
    private val favorite: MutableLiveData<Boolean> = MutableLiveData()

    fun getDetailUser(username: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        val user = githubUserDao.getUserDetail(username)
        if (user != null) {
            favorite.postValue(true)
            emit(Resource.success(user))
        } else {
            favorite.postValue(false)
            try {
                emit(Resource.success(Retrofit.apiClient.userDetail(username)))
            } catch (e: Exception) {
                emit(Resource.error(null, e.message ?: "Error"))
            }
        }
    }

    suspend fun insert(user: UserGithub) {
        githubUserDao.insertUser(user)
        favorite.value = true
    }

    suspend fun delete(user: UserGithub) {
        githubUserDao.deleteUser(user)
        favorite.value = false
    }

    val isFavorite: LiveData<Boolean> = favorite
}