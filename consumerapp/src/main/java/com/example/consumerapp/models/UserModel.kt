package com.example.consumerapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val name: String?,
    val location: String?,
    val type: String?,
    val public_repos: Int,
    val followers: Int,
    val following: Int,
    val htmlUrl: String,
    val company: String
) : Parcelable