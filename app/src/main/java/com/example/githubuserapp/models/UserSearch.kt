package com.example.githubuserapp.models

data class UserSearch(
    val total_count: String,
    val incomplete_results: Boolean? = null,
    val items: List<UserGithub>
)