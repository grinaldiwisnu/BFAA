package com.example.githubuserapp.data.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuserapp.data.model.SearchResponse

@Dao
interface GithubUserDao {
    @Query("SELECT * FROM user_table ORDER BY login ASC")
    fun getUsers(): LiveData<List<SearchResponse>>

    @Query("SELECT * from user_table WHERE login = :username")
    fun getUser(username: String): SearchResponse?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: SearchResponse)

    @Delete
    suspend fun deleteUser(user: SearchResponse): Int

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Query("SELECT * from user_table ORDER BY login ASC")
    fun getUserListProvider(): Cursor

    @Query("SELECT * from user_table ORDER BY login ASC")
    fun getWidgetList(): List<SearchResponse>
}