package com.example.ra1_somativa.feature.data.api

import com.example.ra1_somativa.feature.data.model.Meal
import com.example.ra1_somativa.feature.data.model.MealDetails
import com.example.ra1_somativa.feature.data.model.MealResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal class MealService {

    val mealApi: MealApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        mealApi = retrofit.create(MealApi::class.java)
    }

    suspend fun getMealsByCategory(category: String): List<Meal> {
        return mealApi.getMealByCategory(category).meals
    }

    suspend fun getMealByFirstLetter(letter: String): List<Meal> {
        return mealApi.getMealByFirstLetter(letter).meals
    }

    suspend fun getMealId(id: String): List<MealDetails> {
        return mealApi.getMealById(id).meals
    }

    private companion object {
        private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    }
}