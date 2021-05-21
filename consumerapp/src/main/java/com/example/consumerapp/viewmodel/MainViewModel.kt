package com.example.consumerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.consumerapp.data.datasource.UserDataSource
import com.example.consumerapp.data.repository.UserRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository

    init {
        val source = UserDataSource(application.contentResolver)
        repository = UserRepository(source)
    }

    var userLists = repository.getUserList()
}