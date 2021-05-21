package com.example.githubuserapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp.models.SearchResponse
import com.example.githubuserapp.networks.FavoriteRepository
import com.example.githubuserapp.networks.local.GithubDatabase
import com.example.githubuserapp.networks.local.GithubUserDao
import com.example.githubuserapp.utils.Resource
import kotlinx.coroutines.launch

class DetailViewModel(app: Application) : AndroidViewModel(app) {

    private var userDao: GithubUserDao = GithubDatabase.getDatabase(app).userDao()
    private var favoriteRepository: FavoriteRepository =
        FavoriteRepository(githubUserDao = userDao)

    init {
        favoriteRepository = FavoriteRepository(userDao)
    }

    val isFavorite: LiveData<Boolean> = favoriteRepository.isFavorite

    fun data(username: String): LiveData<Resource<SearchResponse>> =
        favoriteRepository.getDetailUser(username)

    fun addFavorite(githubUser: SearchResponse) = viewModelScope.launch {
        favoriteRepository.insert(githubUser)
    }

    fun removeFavorite(githubUser: SearchResponse) = viewModelScope.launch {
        favoriteRepository.delete(githubUser)
    }
}