package com.example.githubuserapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.githubuserapp.data.FavoriteRepository
import com.example.githubuserapp.data.local.GithubDatabase
import com.example.githubuserapp.data.local.GithubUserDao
import com.example.githubuserapp.data.model.SearchResponse
import com.example.githubuserapp.utils.Resource

class DetailViewModel(app: Application) : AndroidViewModel(app) {

    private var githubUser: GithubUserDao = GithubDatabase.getDatabase(app).userDao()
    private var favoriteRepository: FavoriteRepository =
        FavoriteRepository(githubUserDao = githubUser)

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