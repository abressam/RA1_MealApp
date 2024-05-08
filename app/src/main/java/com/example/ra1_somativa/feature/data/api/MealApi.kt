package com.example.ra1_somativa.feature.data.api

import com.example.ra1_somativa.feature.data.model.MealResponse
import retrofit2.http.GET

internal interface MealApi {
    @GET("filter.php?c=Beef")
    suspend fun getBeefCategory(): MealResponse

    @GET("filter.php?c=Chicken")
    suspend fun getChickenCategory(): MealResponse

    @GET("filter.php?c=Dessert")
    suspend fun getDessertCategory(): MealResponse

    @GET("filter.php?c=Lamb")
    suspend fun getLambCategory(): MealResponse

    @GET("filter.php?c=Miscellaneous")
    suspend fun getMiscellaneousCategory(): MealResponse

    @GET("filter.php?c=Pasta")
    suspend fun getPastaCategory(): MealResponse

    @GET("filter.php?c=Pork")
    suspend fun getPorkCategory(): MealResponse

    @GET("filter.php?c=Seafood")
    suspend fun getSeafoodCategory(): MealResponse

    @GET("filter.php?c=Side")
    suspend fun getSideCategory(): MealResponse

    @GET("filter.php?c=Starter")
    suspend fun getStarterCategory(): MealResponse

    @GET("filter.php?c=Vegan")
    suspend fun getVeganCategory(): MealResponse

    @GET("filter.php?c=Vegetarian")
    suspend fun getVegetarianCategory(): MealResponse

    @GET("filter.php?c=Breakfast")
    suspend fun getBreakfastCategory(): MealResponse

    @GET("filter.php?c=Goat")
    suspend fun getGoatCategory(): MealResponse
}