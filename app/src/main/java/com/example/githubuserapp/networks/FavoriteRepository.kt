package com.example.githubuserapp.networks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.githubuserapp.models.SearchResponse
import com.example.githubuserapp.networks.api.Retrofit
import com.example.githubuserapp.networks.local.GithubUserDao
import com.example.githubuserapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class FavoriteRepository(private val githubUserDao: GithubUserDao) {
    private val favorite: MutableLiveData<Boolean> = MutableLiveData()

    fun getDetailUser(username: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        val user = githubUserDao.getUser(username)
        if (user != null) {
            favorite.postValue(true)
            emit(Resource.success(user))
        } else {
            favorite.postValue(false)
            try {
                emit(Resource.success(Retrofit.apiClient.getSearchByUserName(username)))
            } catch (e: Exception) {
                emit(Resource.error(null, e.message ?: "Error"))
            }
        }
    }

    suspend fun insert(searchResponse: SearchResponse) {
        githubUserDao.insertUser(user = searchResponse)
        favorite.value = true
    }

    suspend fun delete(searchResponse: SearchResponse) {
        githubUserDao.deleteUser(user = searchResponse)
        favorite.value = false
    }

    val isFavorite: LiveData<Boolean> = favorite
}