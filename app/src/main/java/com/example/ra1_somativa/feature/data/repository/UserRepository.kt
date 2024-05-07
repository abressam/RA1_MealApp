package com.example.ra1_somativa.feature.data.repository

import androidx.annotation.WorkerThread
import com.example.ra1_somativa.feature.data.dao.UserDao
import com.example.ra1_somativa.feature.data.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    val allUsers: Flow<List<User>> = userDao.getAllUsers()

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insert(user)
    }
}