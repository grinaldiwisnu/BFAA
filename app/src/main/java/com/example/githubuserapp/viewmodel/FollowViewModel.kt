package com.example.githubuserapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.api.ApiRequest
import com.example.githubuserapp.data.model.SearchResponse
import com.example.githubuserapp.utils.Resource

enum class TypeView {
    FOLLOWER,
    FOLLOWING,
}

class FollowViewModel : ViewModel() {
    private val username: MutableLiveData<String> = MutableLiveData()

    private lateinit var type: TypeView

    val dataFollow: LiveData<Resource<List<SearchResponse>>> = Transformations
        .switchMap(username) {
            when (type) {
                TypeView.FOLLOWER -> {
                    ApiRequest.getFollowers(it)
                }
                TypeView.FOLLOWING -> {
                    ApiRequest.getFollowing(it)
                }
            }
        }

    fun setFollow(user: String, typeView: TypeView) {
        if (username.value == user) {
            return
        }
        username.value = user
        type = typeView
    }
}