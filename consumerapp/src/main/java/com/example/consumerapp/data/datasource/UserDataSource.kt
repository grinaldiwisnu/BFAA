package com.example.consumerapp.data.datasource

import android.content.ContentResolver
import com.example.consumerapp.data.contract.DatabaseContract
import com.example.consumerapp.data.contract.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.example.consumerapp.models.UserModel

class UserDataSource(private val contentResolver: ContentResolver) {

    fun getAllUsers(): List<UserModel> {
        val result: MutableList<UserModel> = mutableListOf()

        val cursor = contentResolver.query(
            CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.apply {
            while (moveToNext()) {
                result.add(
                    UserModel(
                        getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.ID)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOGIN)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR_URL)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOCATION)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.TYPE)),
                        getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.PUBLIC_REPOS)),
                        getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWERS)),
                        getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWING)),
                    )
                )
            }
            close()
        }
        return result.toList()
    }
}