package com.example.ra1_somativa.feature.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.ra1_somativa.feature.data.model.MealEntity

@Dao
interface MealDao {
    @Insert
    suspend fun insertMeal(meal: MealEntity)

    @Query("SELECT * FROM meals WHERE userId = :userId")
    fun getMealsByUserId(userId: Long): Flow<List<MealEntity>>

    @Query("DELETE FROM meals WHERE mealIdApi = :mealIdApi")
    suspend fun deleteFavoriteMeal(mealIdApi: String)

    @Query("SELECT EXISTS (SELECT 1 FROM meals WHERE mealIdApi = :mealIdApi)")
    suspend fun isMealFavorite(mealIdApi: String): Boolean

}