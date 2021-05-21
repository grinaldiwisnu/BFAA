package com.example.consumerapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.consumerapp.data.datasource.UserDataSource
import com.example.consumerapp.models.UserModel
import kotlinx.coroutines.Dispatchers

class UserRepository(private val source: UserDataSource) {

    fun getUserList(): LiveData<List<UserModel>> = liveData(Dispatchers.IO){
        emit(source.getAllUsers())
    }
}