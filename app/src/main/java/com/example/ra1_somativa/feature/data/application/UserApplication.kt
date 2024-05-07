package com.example.ra1_somativa.feature.data.application

import android.app.Application
import com.example.ra1_somativa.feature.data.database.UserRoomDatabase
import com.example.ra1_somativa.feature.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class UserApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { UserRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { UserRepository(database.userDao()) }
}