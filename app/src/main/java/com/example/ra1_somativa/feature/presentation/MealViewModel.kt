package com.example.ra1_somativa.feature.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ra1_somativa.feature.data.api.MealService
import com.example.ra1_somativa.feature.data.model.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealViewModel : ViewModel() {

    private val mealService = MealService()

    private val _mealListInfo: MutableLiveData<List<Meal>> = MutableLiveData()
    val mealListInfo: LiveData<List<Meal>>
        get() = _mealListInfo

    fun fetchData() {
        viewModelScope.launch() {
            try {
                val data = mealService.getDataFromApi()
                _mealListInfo.value = data
            } catch (e: Exception) {
                // Handle error or show error message
            }
        }
    }
}
