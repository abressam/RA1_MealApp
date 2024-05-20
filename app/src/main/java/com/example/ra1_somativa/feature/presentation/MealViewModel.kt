package com.example.ra1_somativa.feature.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ra1_somativa.feature.data.api.MealService
import com.example.ra1_somativa.feature.data.model.Meal
import com.example.ra1_somativa.feature.data.model.MealDetails
import kotlinx.coroutines.launch

class MealViewModel : ViewModel() {

    private val mealService = MealService()

    private val _mealListInfo: MutableLiveData<List<Meal>> = MutableLiveData()
    val mealListInfo: LiveData<List<Meal>>
        get() = _mealListInfo

    private val _mealDetailsInfo: MutableLiveData<List<MealDetails>> = MutableLiveData()
    val mealDetailsInfo: LiveData<List<MealDetails>>
        get() = _mealDetailsInfo

    private val _mealInfo: MutableLiveData<List<Meal>> = MutableLiveData()
    val mealInfo: LiveData<List<Meal>>
        get() = _mealInfo

    private val _errorEvent: MutableLiveData<String> = MutableLiveData()
    val errorEvent: LiveData<String>
        get() = _errorEvent

    fun fetchDataByCategory(category: String) {
        viewModelScope.launch() {
            try {
                val data = mealService.getMealsByCategory(category)
                _mealListInfo.value = data
            } catch (e: Exception) {
                _mealListInfo.value = emptyList()
                _errorEvent.value = "Failed to fetch meals by category"
            }
        }
    }

    fun fetchDataById(id: String) {
        viewModelScope.launch() {
            try {
                val dataId = mealService.getMealId(id)
                _mealDetailsInfo.value = dataId
            } catch (e: Exception) {
                _mealDetailsInfo.value = emptyList()
                _errorEvent.value = "Failed to fetch meal details"
            }
        }
    }

    fun fetchDataByFirstLetter(letter: String) {
        viewModelScope.launch {
            try {
                val data = mealService.getMealByFirstLetter(letter)

                if (data.isNullOrEmpty()) {
                    _mealInfo.postValue(emptyList())
                    _errorEvent.value = "No meals found"
                } else {
                    _mealInfo.postValue(data)
                }

            } catch (e: Exception) {
                _mealInfo.value = emptyList()
                _errorEvent.value = "Failed to fetch meals by first letter"
            }
        }
    }
}
