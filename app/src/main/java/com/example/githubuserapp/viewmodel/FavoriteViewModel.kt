package com.example.githubuserapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuserapp.data.GithubRepository
import com.example.githubuserapp.data.local.GithubDatabase
import com.example.githubuserapp.models.UserGithub

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {
    val dataFavorite: LiveData<List<UserGithub>>

    init {
        val githubUserDao = GithubDatabase.getInstance(app).userDao()
        dataFavorite = GithubRepository.getFavoriteUsers(githubUserDao)
    }
}