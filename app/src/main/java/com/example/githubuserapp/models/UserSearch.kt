package com.example.githubuserapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserSearch(
    val total_count: String,
    val incomplete_results: Boolean? = null,
    val items: List<UserGithub>
) : Parcelable