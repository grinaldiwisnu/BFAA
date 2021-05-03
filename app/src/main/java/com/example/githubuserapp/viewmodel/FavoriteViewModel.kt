package com.example.githubuserapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuserapp.data.GithubRepository
import com.example.githubuserapp.data.local.GithubDatabase
import com.example.githubuserapp.data.model.SearchResponse

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {
    val dataFavorite: LiveData<List<SearchResponse>>

    init {
        val githubUserDao = GithubDatabase.getDatabase(app).userDao()
        dataFavorite = GithubRepository.getFavorite(githubUserDao = githubUserDao)
    }
}