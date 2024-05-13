package com.example.ra1_somativa.feature.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "users",
    indices = [Index(value = ["email"], unique = true)]
)
data class User (
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "password")
    val password: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    val userId: Long? = null
)