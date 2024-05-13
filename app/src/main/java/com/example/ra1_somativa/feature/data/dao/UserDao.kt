package com.example.ra1_somativa.feature.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.ra1_somativa.feature.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Query("DELETE FROM users")
    suspend fun deleteAll()

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?
}