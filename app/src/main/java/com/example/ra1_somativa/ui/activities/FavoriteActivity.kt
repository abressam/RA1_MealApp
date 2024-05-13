package com.example.ra1_somativa.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.ra1_somativa.databinding.ActivityFavoriteBinding
import com.example.ra1_somativa.feature.data.application.UserApplication
import com.example.ra1_somativa.feature.data.model.MealDetails
import com.example.ra1_somativa.feature.data.model.UserDataManager
import com.example.ra1_somativa.feature.presentation.FavoriteMealViewModel
import com.example.ra1_somativa.feature.presentation.FavoriteMealViewModelFactory
import com.example.ra1_somativa.feature.presentation.MealViewModel
import com.example.ra1_somativa.ui.adapter.FavoriteMealAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: MealViewModel by viewModels()
    private val favoriteMeal: FavoriteMealViewModel by viewModels {
        FavoriteMealViewModelFactory((application as UserApplication).mealRepository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recycleView
        val adapter = FavoriteMealAdapter()

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val userId = UserDataManager.getUserId()
        Log.d("FavoriteActivity", "Valor do ID do usuário: $userId")

        if (userId != null) {
            favoriteMeal.getMealsByUserId(userId)
            Log.d("FavoriteActivity", "getMealsByUserId() chamado para o usuário com ID: $userId")

            favoriteMeal.mealsLiveData.observe(this) { meals  ->
                Log.d("FavoriteActivity", "Refeições observadas na FavoriteActivity: $meals")
                adapter.updateList(meals)
            }
        } else {
            Log.d("MainActivity", "User ID não encontrado")
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}