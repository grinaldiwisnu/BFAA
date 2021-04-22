package com.example.githubuserapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserSearch(
    val total_count: String,
    val incomplete_results: Boolean? = null,
    val items: List<SearchResponse>
) : Parcelable