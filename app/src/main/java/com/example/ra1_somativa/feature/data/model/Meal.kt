package com.example.ra1_somativa.feature.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Meal (
    @SerializedName("idMeal") val idMeal: String,
    @SerializedName("strMeal") val strMeal: String,
    @SerializedName("strMealThumb") val strMealThumb: String
)

data class MealResponse(
    @Json(name = "meals") val meals: List<Meal>
)
