package com.example.githubuserapp.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.githubuserapp.R
import com.example.githubuserapp.models.SearchResponse
import com.example.githubuserapp.networks.local.GithubDatabase
import com.example.githubuserapp.networks.local.GithubUserDao

class WidgetProvider(private val context: Context): RemoteViewsService.RemoteViewsFactory {
    private lateinit var listUser: List<SearchResponse>
    private lateinit var githubUserDao: GithubUserDao

    override fun onCreate() {
        githubUserDao = GithubDatabase.getDatabase(context).userDao()
    }

    override fun onDataSetChanged() {
        listUser = githubUserDao.getWidgetList()
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

    override fun getItemId(position: Int): Long = listUser[position].id.toLong()

    override fun hasStableIds(): Boolean = true
}