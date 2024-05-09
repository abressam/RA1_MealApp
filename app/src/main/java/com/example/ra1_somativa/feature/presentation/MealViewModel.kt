package com.example.ra1_somativa.feature.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ra1_somativa.feature.data.api.MealService
import com.example.ra1_somativa.feature.data.model.Meal
import com.example.ra1_somativa.feature.data.model.MealDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealViewModel : ViewModel() {

    private val mealService = MealService()

    private val _mealListInfo: MutableLiveData<List<Meal>> = MutableLiveData()
    val mealListInfo: LiveData<List<Meal>>
        get() = _mealListInfo

    private val _mealDetailsInfo: MutableLiveData<List<MealDetails>> = MutableLiveData()
    val mealDetailsInfo: LiveData<List<MealDetails>>
        get() = _mealDetailsInfo

    fun fetchDataByCategory(category: String) {
        viewModelScope.launch() {
            try {
                val data = mealService.getMealsByCategory(category)
                _mealListInfo.value = data
            } catch (e: Exception) {
                // Handle error or show error message
            }
        }
    }

    fun fetchDataById(id: String) {
        viewModelScope.launch() {
            try {
                val dataId = mealService.getMealId(id)
                _mealDetailsInfo.value = dataId
            } catch (e: Exception) {
                // Handle error or show error message
            }
        }
    }
}
