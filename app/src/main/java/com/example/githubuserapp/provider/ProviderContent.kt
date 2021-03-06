package com.example.githubuserapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubuserapp.data.local.GithubDatabase
import com.example.githubuserapp.data.local.GithubUserDao

class ProviderContent : ContentProvider() {
    private lateinit var userDao: GithubUserDao

    companion object {
        private const val AUTHORITY = "com.example.githubuserapp"
        private const val TABLE_NAME = "users"
        private const val USERLIST = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(
                AUTHORITY,
                TABLE_NAME,
                USERLIST
            )
        }
    }

    override fun onCreate(): Boolean {
        userDao = GithubDatabase.getInstance(context as Context).userDao()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            USERLIST -> userDao.getUserListProvider()
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}