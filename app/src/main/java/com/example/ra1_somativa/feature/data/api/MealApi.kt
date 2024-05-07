package com.example.ra1_somativa.feature.data.api

import com.example.ra1_somativa.feature.data.model.MealResponse
import retrofit2.http.GET

internal interface MealApi {
    @GET("filter.php?a=American")
    suspend fun getMeal(): MealResponse
}