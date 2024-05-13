package com.example.ra1_somativa.feature.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ra1_somativa.feature.data.model.MealEntity
import com.example.ra1_somativa.feature.data.repository.MealRepository
import androidx.lifecycle.viewModelScope
import com.example.ra1_somativa.feature.data.model.Meal
import android.util.Log
import com.example.ra1_somativa.feature.data.model.MealDetails
import com.example.ra1_somativa.feature.data.api.MealService
import com.example.ra1_somativa.feature.data.model.UserDataManager
import kotlinx.coroutines.launch

class FavoriteMealViewModel (
    private val repository: MealRepository,
) : ViewModel() {
    private val mealService = MealService()
    private val userId = UserDataManager.getUserId() ?: -1L

    private val _mealsLiveData = MutableLiveData<List<Meal>>()
    val mealsLiveData: LiveData<List<Meal>> get() = _mealsLiveData

    fun insertMeal(mealEntity: MealEntity) = viewModelScope.launch {
        repository.insertMeal(mealEntity)
    }

    private val _isMealLiked = MutableLiveData<Boolean>()
    val isMealLiked: LiveData<Boolean> get() = _isMealLiked

    fun toggleMealFavoriteStatus(mealIdApi: String) {
        viewModelScope.launch {
            val isFavorite = repository.isMealFavorite(mealIdApi)
            if (isFavorite) {
                repository.deleteFavoriteMeal(mealIdApi)
                _isMealLiked.postValue(false)
            } else {
                // Suponha que você tenha uma instância de MealEntity para adicionar como favorita
                val meal = MealEntity(mealIdApi = mealIdApi, userId = userId) // Defina o userId conforme necessário
                repository.insertMeal(meal)
                _isMealLiked.postValue(true)
            }
        }
    }

    fun getMealsByUserId(userId: Long) {
        viewModelScope.launch {
            Log.d("FavoriteMealViewModel", "Buscando refeições para o usuário com ID: $userId")
            repository.getMealsByUserId(userId).collect { mealEntities ->
                // Converter MealEntity em Meal e atualizar _mealsLiveData
                val mealsList = mealEntities.map { mealEntity ->
                    val meal = Meal(
                        idMeal = mealEntity.mealIdApi,
                        strMeal = "",
                        strMealThumb = ""
                    )

                    // Obter os detalhes da refeição usando fetchDataById
                    try {
                        val mealDetails = fetchDataById(mealEntity.mealIdApi)
                        // Atualizar os campos strMeal e strMealThumb com os detalhes obtidos
                        meal.strMeal = mealDetails.strMeal
                        meal.strMealThumb = mealDetails.strMealThumb
                    } catch (e: Exception) {
                        // Tratar erros ao obter os detalhes da refeição
                        Log.e("FavoriteMealViewModel", "Erro ao obter detalhes da refeição", e)
                    }

                    meal
                }
                _mealsLiveData.postValue(mealsList)
            }
        }
    }

    private suspend fun fetchDataById(id: String): MealDetails {
        val mealDetailsList = mealService.getMealId(id)
        if (mealDetailsList.isNotEmpty()) {
            return mealDetailsList[0] // Retorna o primeiro elemento da lista
        } else {
            throw IllegalStateException("Nenhum detalhe de refeição encontrado para o ID: $id")
        }
    }

}

class FavoriteMealViewModelFactory(private val repository: MealRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteMealViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteMealViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}