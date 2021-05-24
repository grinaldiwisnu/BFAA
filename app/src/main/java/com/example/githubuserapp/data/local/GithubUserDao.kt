package com.example.githubuserapp.data.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuserapp.models.UserGithub

@Dao
interface GithubUserDao {
    @Query("SELECT * from user_table ORDER BY login ASC")
    fun getUserList(): LiveData<List<UserGithub>>

    @Query("SELECT * from user_table WHERE login = :username")
    fun getUserDetail(username: String): UserGithub?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserGithub)

    @Delete
    suspend fun deleteUser(model: UserGithub): Int

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Query("SELECT * from user_table ORDER BY login ASC")
    fun getUserListProvider(): Cursor

    @Query("SELECT * from user_table ORDER BY login ASC")
    fun getWidgetList(): List<UserGithub>
}