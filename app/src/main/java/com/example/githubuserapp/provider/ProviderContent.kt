package com.example.githubuserapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubuserapp.networks.local.GithubDatabase
import com.example.githubuserapp.networks.local.GithubUserDao

class ProviderContent : ContentProvider() {
    private lateinit var githubUserDao: GithubUserDao
    override fun onCreate(): Boolean {
        githubUserDao = GithubDatabase.getDatabase(context = context as Context).userDao()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            USERLIST -> githubUserDao.getUserListProvider()
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    companion object {
        private const val AUTHORITY = "com.example.githubuserapp"
        private const val TABLE_NAME = "user_table"
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
}