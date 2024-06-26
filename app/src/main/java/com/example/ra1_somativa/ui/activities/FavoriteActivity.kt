package com.example.ra1_somativa.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.ra1_somativa.databinding.ActivityFavoriteBinding
import com.example.ra1_somativa.feature.data.application.UserApplication
import com.example.ra1_somativa.feature.data.model.Meal
import com.example.ra1_somativa.feature.data.model.MealDetails
import com.example.ra1_somativa.feature.data.model.UserDataManager
import com.example.ra1_somativa.feature.presentation.FavoriteMealViewModel
import com.example.ra1_somativa.feature.presentation.FavoriteMealViewModelFactory
import com.example.ra1_somativa.feature.presentation.MealViewModel
import com.example.ra1_somativa.ui.adapter.FavoriteMealAdapter

class FavoriteActivity : AppCompatActivity(), FavoriteMealAdapter.OnItemClickListener {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteMeal: FavoriteMealViewModel by viewModels {
        FavoriteMealViewModelFactory((application as UserApplication).mealRepository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recycleView
        val adapter = FavoriteMealAdapter(this)

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
                toggleEmptyMessage(meals.isEmpty())
            }
        } else {
            Log.d("MainActivity", "User ID não encontrado")
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    override fun onItemClick(meal: Meal) {
        val intent = Intent(this, MealDetailsActivity::class.java)
        val bundle = Bundle()

        bundle.putString("mealId", meal.idMeal)

        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun toggleEmptyMessage(isEmpty: Boolean) {
        binding.emptyTextView.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }
}