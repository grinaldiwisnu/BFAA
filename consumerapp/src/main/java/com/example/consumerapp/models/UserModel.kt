package com.example.consumerapp.models

data class UserModel(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val name: String?,
    val location: String?,
    val type: String?,
    val public_repos: Int,
    val followers: Int,
    val following: Int
)