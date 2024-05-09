package com.example.ra1_somativa.feature.data.api

import com.example.ra1_somativa.feature.data.model.MealDetailsResponse
import com.example.ra1_somativa.feature.data.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MealApi {
    @GET("filter.php")
    suspend fun getMealByCategory(@Query("c") category: String): MealResponse

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") mealId: String): MealDetailsResponse
}