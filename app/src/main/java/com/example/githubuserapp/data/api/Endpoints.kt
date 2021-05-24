package com.example.githubuserapp.data.api

import com.example.githubuserapp.models.UserGithub
import com.example.githubuserapp.models.UserSearch
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoints {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q")
        q: String?
    ): UserSearch

    @GET("users/{username}")
    suspend fun userDetail(
        @Path("username")
        username: String?
    ): UserGithub

    @GET("users/{username}/followers")
    suspend fun userFollower(
        @Path("username")
        username: String?
    ): List<UserGithub>

    @GET("users/{username}/following")
    suspend fun userFollowing(
        @Path("username")
        username: String?
    ): List<UserGithub>
}