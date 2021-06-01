package com.example.githubuserapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuserapp.models.UserGithub

@Database(entities = [UserGithub::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun userDao(): GithubUserDao

    companion object {
        private const val DB_NAME = "github_db"

        @Volatile
        private var instance: GithubDatabase? = null

        fun getInstance(context: Context): GithubDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context,
                        GithubDatabase::class.java, DB_NAME
                    ).build()
                }
            }
            return instance as GithubDatabase
        }
    }
}