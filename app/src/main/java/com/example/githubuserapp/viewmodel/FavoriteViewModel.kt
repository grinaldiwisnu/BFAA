package com.example.githubuserapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuserapp.models.SearchResponse
import com.example.githubuserapp.networks.GithubRepository
import com.example.githubuserapp.networks.local.GithubDatabase

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {
    val dataFavorite: LiveData<List<SearchResponse>>

    init {
        val githubUserDao = GithubDatabase.getDatabase(app).userDao()
        dataFavorite = GithubRepository.getFavorite(githubUserDao)
    }
}