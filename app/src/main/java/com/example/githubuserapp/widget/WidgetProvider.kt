package com.example.githubuserapp.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.githubuserapp.R
import com.example.githubuserapp.data.local.GithubDatabase
import com.example.githubuserapp.data.local.GithubUserDao
import com.example.githubuserapp.models.UserGithub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class WidgetProvider(private val context: Context): RemoteViewsService.RemoteViewsFactory {
    private lateinit var listUser: List<UserGithub>
    private lateinit var githubUserDao: GithubUserDao

    override fun onCreate() {
        githubUserDao = GithubDatabase.getInstance(context).userDao()
        fetchUserData()
    }

    override fun onDataSetChanged() {
        fetchUserData()
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int {
        return this.listUser.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.list_widget)
        remoteViews.setTextViewText(R.id.text1, listUser[position].login)
        return remoteViews
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = listUser[position].id?.toLong()!!

    override fun hasStableIds(): Boolean = true

    private fun fetchUserData() {
        runBlocking(Dispatchers.IO) {
            val users = githubUserDao.getWidgetList()
            listUser = users
        }
    }
}