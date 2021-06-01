package com.example.githubuserapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp.data.FavoriteRepository
import com.example.githubuserapp.data.local.GithubDatabase
import com.example.githubuserapp.data.local.GithubUserDao
import com.example.githubuserapp.models.UserGithub
import com.example.githubuserapp.utils.Resource
import kotlinx.coroutines.launch

class DetailViewModel(app: Application) : AndroidViewModel(app) {
    private var userDao: GithubUserDao = GithubDatabase.getInstance(app).userDao()
    private var favoriteRepository: FavoriteRepository

    init {
        favoriteRepository = FavoriteRepository(userDao)
    }

    fun data(username: String?): LiveData<Resource<UserGithub>> =
        favoriteRepository.getDetailUser(username)

    fun addFavorite(model: UserGithub) = viewModelScope.launch {
        favoriteRepository.insert(model)
    }

    fun removeFavorite(model: UserGithub) = viewModelScope.launch {
        favoriteRepository.delete(model)
    }

    val isFavorite: LiveData<Boolean> = favoriteRepository.isFavorite
}