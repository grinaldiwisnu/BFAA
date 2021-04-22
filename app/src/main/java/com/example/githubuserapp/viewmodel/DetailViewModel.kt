package com.example.githubuserapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.api.ApiRequest
import com.example.githubuserapp.data.model.UserDetail
import com.example.githubuserapp.utils.Resource

class DetailViewModel : ViewModel() {

    private val username: MutableLiveData<String> = MutableLiveData()

    val dataDetail: LiveData<Resource<UserDetail>> = Transformations
        .switchMap(username) {
            ApiRequest.getDetailUser(it)
        }

    fun setDetail(userid: String) {
        if (username.value == userid) {
            return
        }
        username.value = userid
    }
}