package com.example.ra1_somativa.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import android.widget.Toast
import android.content.Intent
import com.example.ra1_somativa.databinding.ActivityMealDetailsBinding
import com.example.ra1_somativa.feature.data.model.MealDetails
import com.example.ra1_somativa.feature.presentation.MealViewModel
import coil.load
import com.example.ra1_somativa.R
import com.example.ra1_somativa.feature.data.application.UserApplication
import com.example.ra1_somativa.feature.data.model.MealEntity
import com.example.ra1_somativa.feature.data.model.UserDataManager
import com.example.ra1_somativa.feature.presentation.FavoriteMealViewModel
import com.example.ra1_somativa.feature.presentation.FavoriteMealViewModelFactory
import com.example.ra1_somativa.feature.presentation.UserViewModel
import com.example.ra1_somativa.feature.presentation.UserViewModelFactory
import com.example.ra1_somativa.ui.fragment.CheckboxFragment

class MealDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealDetailsBinding
    private val viewModel: MealViewModel by viewModels()
    private val favoriteMeal: FavoriteMealViewModel by viewModels {
        FavoriteMealViewModelFactory((application as UserApplication).mealRepository)
    }
    private var isMealLiked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMealDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealId = intent.getStringExtra("mealId")
        val userId = UserDataManager.getUserId()

        Log.d("MealDetailsActivity", "Valor do id: $mealId")

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
                val newFavoriteMeal = MealEntity(mealIdApi = mealId, userId = userId)
                saveMeal(newFavoriteMeal)

                Log.d("MealDetailsActivity", "Receita salva para o usuário com ID: $userId")

                val intent = Intent(this, FavoriteActivity::class.java)
                intent.putExtra("userId", userId)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Erro ao salvar a receita", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveMeal(mealEntity: MealEntity) {
        val mealSaved = true // Indica se a refeição foi salva com sucesso

        if (mealSaved) {
            // Alterar o estado do botão
            isMealLiked = !isMealLiked

            // Alterar a imagem do botão com base no estado atual
            if (isMealLiked) {
                favoriteMeal.insertMeal(mealEntity)
                Toast.makeText(this, "Recipe save in 'Favorites'", Toast.LENGTH_SHORT).show()
                binding.likeButton.setImageResource(android.R.drawable.btn_star_big_on)
            } else {
                Toast.makeText(this, "Recipe remove from 'Favorites'", Toast.LENGTH_SHORT).show()
                binding.likeButton.setImageResource(android.R.drawable.btn_star_big_off)
            }
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