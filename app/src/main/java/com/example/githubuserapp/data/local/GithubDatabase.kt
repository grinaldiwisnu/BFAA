package com.example.githubuserapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuserapp.data.model.SearchResponse

@Database(entities = [SearchResponse::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: GithubDatabase? = null

        fun getDatabase(context: Context): GithubDatabase {
            val mInstance = INSTANCE
            if (mInstance != null)
                return mInstance

            synchronized(GithubDatabase::class) {
                val mbuilder = Room.databaseBuilder(
                    context.applicationContext, GithubDatabase::class.java, "github"
                ).build()
                INSTANCE = mbuilder
                return mbuilder
            }
        }
    }

    abstract fun userDao(): GithubUserDao
}