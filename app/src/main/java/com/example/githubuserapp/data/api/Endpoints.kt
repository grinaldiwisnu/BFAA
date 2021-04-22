package com.example.githubuserapp.data.api

import com.example.githubuserapp.data.model.SearchResponse
import com.example.githubuserapp.data.model.UserDetail
import com.example.githubuserapp.data.model.UserSearch
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoints {
    @GET("search/users")
    suspend fun getSearchUser(
        @Query("q") query: String?
    ): UserSearch

    @GET("users/{username}")
    suspend fun getSearchByUserName(
        @Path("username") username: String
    ): UserDetail

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ): List<SearchResponse>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ): List<SearchResponse>
}