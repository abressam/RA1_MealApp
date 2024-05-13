package com.example.ra1_somativa.feature.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "meals",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class MealEntity (

    @ColumnInfo(name = "mealIdApi")
    val mealIdApi: String,

    @ColumnInfo(name = "userId")
    val userId: Long,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mealId")
    val mealId: Long? = null
)