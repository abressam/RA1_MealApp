package com.example.ra1_somativa.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ra1_somativa.R
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ra1_somativa.databinding.ActivityHomeBinding
import com.example.ra1_somativa.feature.data.model.Meal
import com.example.ra1_somativa.feature.presentation.MealViewModel
import com.example.ra1_somativa.ui.adapter.MealAdapter
import android.content.Intent
import com.example.ra1_somativa.feature.data.application.UserApplication
import com.example.ra1_somativa.feature.data.model.UserDataManager
import com.example.ra1_somativa.feature.presentation.UserViewModel
import com.example.ra1_somativa.feature.presentation.UserViewModelFactory

class HomeActivity : AppCompatActivity(), MealAdapter.OnItemClickListener {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: MealViewModel by viewModels()
    private lateinit var userEmail: String
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as UserApplication).repository)
    }

    private val categoryButtonMap = mapOf(
        R.id.categoryBeefButton to "Beef",
        R.id.categoryChickenButton to "Chicken",
        R.id.categoryDessertButton to "Dessert",
        R.id.categoryLambButton to "Lamb",
        R.id.categoryMiscellaneousButton to "Miscellaneous",
        R.id.categoryPastaButton to "Pasta",
        R.id.categoryPorkButton to "Pork",
        R.id.categorySeafoodButton to "Seafood",
        R.id.categorySideButton to "Side",
        R.id.categoryStarterButton to "Starter",
        R.id.categoryVeganButton to "Vegan",
        R.id.categoryVegetarianButton to "Vegetarian",
        R.id.categoryBreakfastButton to "Breakfast",
        R.id.categoryGoatButton to "Goat"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userEmail = intent.getStringExtra("email") ?: ""

        if (userEmail != null) {
            userViewModel.getUserByEmail(userEmail).observe(this) { user ->
                user?.let {
                    val userId = user.userId ?: -1L

                    UserDataManager.setUserId(userId)
                }
            }
        }

        val recyclerView = binding.recycleView
        val adapter = MealAdapter(this)

        val layoutManager = GridLayoutManager(this, 2)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        selectButton(R.id.categoryBeefButton)
        viewModel.fetchDataByCategory("Beef")

        binding.favoriteButton.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        categoryButtonMap.forEach { (buttonId, category) ->
            val button = getButtonById(buttonId)

            button.setOnClickListener {
                selectButton(buttonId)
                viewModel.fetchDataByCategory(category)
            }
        }

        viewModel.mealListInfo.observe(this) { meals ->
            adapter.updateList(meals)
        }
    }

    override fun onItemClick(meal: Meal) {
        val intent = Intent(this, MealDetailsActivity::class.java)
        val bundle = Bundle()

        bundle.putString("mealId", meal.idMeal)
        bundle.putString("email", userEmail)

        intent.putExtras(bundle)
        startActivity(intent)
    }

    // Método para selecionar um botão específico
    private fun selectButton(buttonId: Int) {
        categoryButtonMap.keys.forEach { id ->
            val button = getButtonById(id)
            if (id == buttonId) {
                button.setBackgroundResource(R.color.white)
            } else {
                button.setBackgroundResource(R.color.black)
            }
        }
    }

    // Método para obter o botão correspondente ao ID
    private fun getButtonById(buttonId: Int): Button {
        return when (buttonId) {
            R.id.categoryBeefButton -> binding.categoryBeefButton
            R.id.categoryChickenButton -> binding.categoryChickenButton
            R.id.categoryDessertButton -> binding.categoryDessertButton
            R.id.categoryLambButton -> binding.categoryLambButton
            R.id.categoryMiscellaneousButton -> binding.categoryMiscellaneousButton
            R.id.categoryPastaButton -> binding.categoryPastaButton
            R.id.categoryPorkButton -> binding.categoryPorkButton
            R.id.categorySeafoodButton -> binding.categorySeafoodButton
            R.id.categorySideButton -> binding.categorySideButton
            R.id.categoryStarterButton -> binding.categoryStarterButton
            R.id.categoryVeganButton -> binding.categoryVeganButton
            R.id.categoryVegetarianButton -> binding.categoryVegetarianButton
            R.id.categoryBreakfastButton -> binding.categoryBreakfastButton
            R.id.categoryGoatButton -> binding.categoryGoatButton
            else -> throw IllegalArgumentException("Button ID not found: $buttonId")
        }
    }
}