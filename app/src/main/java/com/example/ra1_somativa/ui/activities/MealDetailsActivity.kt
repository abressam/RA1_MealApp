package com.example.ra1_somativa.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.ra1_somativa.databinding.ActivityMealDetailsBinding
import com.example.ra1_somativa.feature.data.model.MealDetails
import com.example.ra1_somativa.feature.presentation.MealViewModel
import coil.load
import com.example.ra1_somativa.R
import com.example.ra1_somativa.feature.data.application.UserApplication
import com.example.ra1_somativa.feature.data.model.UserDataManager
import com.example.ra1_somativa.feature.presentation.FavoriteMealViewModel
import com.example.ra1_somativa.feature.presentation.FavoriteMealViewModelFactory
import android.content.Context
import android.content.SharedPreferences
import com.example.ra1_somativa.ui.fragment.CheckboxFragment

class MealDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealDetailsBinding
    private val viewModel: MealViewModel by viewModels()
    private val favoriteMealViewModel: FavoriteMealViewModel by viewModels {
        FavoriteMealViewModelFactory((application as UserApplication).mealRepository)
    }
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMealDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val mealId = intent.getStringExtra("mealId")
        val userId = UserDataManager.getUserId()

        if (mealId != null) {
            viewModel.fetchDataById(mealId.toString())
        }

        viewModel.mealDetailsInfo.observe(this) { mealDetails ->
            mealDetails?.let { displayMealDetails(it) }
        }

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val checkboxFragment = CheckboxFragment()
        fragmentTransaction.replace(R.id.container, checkboxFragment)
        fragmentTransaction.commit()

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.likeButton.setOnClickListener {
            if (mealId != null && userId != null) {
                // Chamando toggleMealFavoriteStatus
                favoriteMealViewModel.toggleMealFavoriteStatus(mealId)

                // Atualizar o estado do botão somente após a operação ser concluída com sucesso
                favoriteMealViewModel.isMealLiked.observe(this) { isLiked ->
                    isLiked?.let {
                        updateLikeButtonState(it)
                        sharedPreferences.edit().putBoolean("isMealLiked", it).apply()
                    }
                }
            }
        }

        // Restaurar o estado do botão ao abrir a Activity
        val isMealLiked = sharedPreferences.getBoolean("isMealLiked", false)
        updateLikeButtonState(isMealLiked)
    }

    private fun updateLikeButtonState(isLiked: Boolean) {
        if (isLiked) {
            binding.likeButton.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            binding.likeButton.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }

    private fun displayMealDetails(mealDetailsList: List<MealDetails>) {
        if (mealDetailsList.isNotEmpty()) {
            val mealDetails = mealDetailsList[0]
            binding.apply {
                strMeal.text = mealDetails.strMeal
                strCategory.text = mealDetails.strCategory
                strInstructions.text = mealDetails.strInstructions
                strMealThumb.load(mealDetails.strMealThumb)
                strArea.text = mealDetails.strArea
            }
        }
    }
}