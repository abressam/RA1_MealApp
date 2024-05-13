package com.example.ra1_somativa.feature.data.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Meal (
    @SerializedName("idMeal") val idMeal: String,
    @SerializedName("strMeal") var strMeal: String,
    @SerializedName("strMealThumb") var strMealThumb: String
)

data class MealResponse(
    @Json(name = "meals") val meals: List<Meal>
)
