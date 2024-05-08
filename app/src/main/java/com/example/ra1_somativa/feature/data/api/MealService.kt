package com.example.ra1_somativa.feature.data.api

import com.example.ra1_somativa.feature.data.model.Meal
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
        return when (category) {
            "Beef" -> mealApi.getBeefCategory().meals
            "Chicken" -> mealApi.getChickenCategory().meals
            "Dessert" -> mealApi.getDessertCategory().meals
            "Lamb" -> mealApi.getLambCategory().meals
            "Miscellaneous" -> mealApi.getMiscellaneousCategory().meals
            "Pasta" -> mealApi.getPastaCategory().meals
            "Pork" -> mealApi.getPorkCategory().meals
            "Seafood" -> mealApi.getSeafoodCategory().meals
            "Starter" -> mealApi.getStarterCategory().meals
            "Vegan" -> mealApi.getVeganCategory().meals
            "Vegetarian" -> mealApi.getVegetarianCategory().meals
            "Breakfast" -> mealApi.getBreakfastCategory().meals
            "Goat" -> mealApi.getGoatCategory().meals
            else -> emptyList()
        }
    }

    private companion object {
        private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    }
}