package com.example.githubuserapp.data.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuserapp.models.UserGithub

@Dao
interface GithubUserDao {
    @Query("SELECT * from users ORDER BY login ASC")
    fun getUserList(): LiveData<List<UserGithub>>

    @Query("SELECT * from users WHERE login = :username")
    fun getUserDetail(username: String?): UserGithub?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserGithub)

    @Delete
    suspend fun deleteUser(model: UserGithub): Int

    @Query("DELETE FROM users")
    suspend fun deleteAll()

    @Query("SELECT * from users ORDER BY login ASC")
    fun getUserListProvider(): Cursor

    @Query("SELECT * from users ORDER BY login ASC")
    fun getWidgetList(): List<UserGithub>

    @Query("select count(*) != 0 from users u where u.login = :username")
    fun checkAvailability(username: String): LiveData<Boolean>
}