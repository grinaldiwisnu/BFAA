package com.example.githubuserapp.widget

import android.content.Intent
import android.widget.RemoteViewsService

class WidgetService: RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return WidgetProvider(this.applicationContext)
    }
}