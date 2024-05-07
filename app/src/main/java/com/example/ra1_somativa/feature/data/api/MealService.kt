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

    suspend fun getDataFromApi(): List<Meal> {
        return mealApi.getMeal().meals // chamada da API
    }

    private companion object {
        private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    }
}