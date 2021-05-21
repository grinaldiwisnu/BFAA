package com.example.githubuserapp.utils

import com.example.githubuserapp.databinding.FragmentFavoriteBinding
import com.example.githubuserapp.databinding.FragmentFollowBinding
import com.example.githubuserapp.databinding.FragmentHomeBinding

interface ShowState {
    fun homeLoading(homeBinding: FragmentHomeBinding): Int? = null
    fun homeSuccess(homeBinding: FragmentHomeBinding): Int? = null
    fun homeError(homeBinding: FragmentHomeBinding, message: String?): Int? = null

    fun followLoading(followBinding: FragmentFollowBinding): Int? = null
    fun followSuccess(followBinding: FragmentFollowBinding): Int? = null
    fun followError(followBinding: FragmentFollowBinding, message: String?): Int? = null

    fun favoriteLoading(favoriteBinding: FragmentFavoriteBinding): Int? = null
    fun favoriteSuccess(favoriteBinding: FragmentFavoriteBinding): Int? = null
    fun favoriteError(favoriteBinding: FragmentFavoriteBinding, message: String?): Int? = null
}