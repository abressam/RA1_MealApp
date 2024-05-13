package com.example.ra1_somativa.feature.data.repository

import androidx.annotation.WorkerThread
import com.example.ra1_somativa.feature.data.dao.MealDao
import com.example.ra1_somativa.feature.data.model.MealEntity
import kotlinx.coroutines.flow.Flow

class MealRepository (private val mealDao: MealDao) {
    suspend fun getMealsByUserId(userId: Long): Flow<List<MealEntity>> {
        return mealDao.getMealsByUserId(userId)
    }

    @WorkerThread
    suspend fun deleteFavoriteMeal(mealIdApi: String) {
        mealDao.deleteFavoriteMeal(mealIdApi)
    }

    suspend fun isMealFavorite(mealIdApi: String): Boolean {
        return mealDao.isMealFavorite(mealIdApi)
    }

    @WorkerThread
    suspend fun insertMeal(mealEntity: MealEntity) {
        return mealDao.insertMeal(mealEntity)
    }
}