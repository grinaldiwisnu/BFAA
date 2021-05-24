package com.example.githubuserapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuserapp.models.UserGithub

@Database(entities = [UserGithub::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: GithubDatabase? = null

        fun getInstance(context: Context): GithubDatabase {
            val mInstance = INSTANCE
            if (mInstance != null)
                return mInstance

            synchronized(this) {
                val mbuilder = Room.databaseBuilder(
                    context, GithubDatabase::class.java, "github_db"
                ).build()
                INSTANCE = mbuilder
                return mbuilder
            }
        }
    }
    abstract fun userDao(): GithubUserDao
}