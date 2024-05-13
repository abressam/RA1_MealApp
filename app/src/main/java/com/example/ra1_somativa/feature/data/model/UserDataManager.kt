package com.example.ra1_somativa.feature.data.model

object UserDataManager {
    private var userId: Long? = null

    fun setUserId(id: Long) {
        userId = id
    }

    fun getUserId(): Long? {
        return userId
    }
}
