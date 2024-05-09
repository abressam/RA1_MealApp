package com.example.ra1_somativa.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import androidx.activity.viewModels
import android.widget.LinearLayout
import com.example.ra1_somativa.databinding.ActivityMealDetailsBinding
import com.example.ra1_somativa.feature.data.model.MealDetails
import com.example.ra1_somativa.feature.presentation.MealViewModel
import coil.load
import com.example.ra1_somativa.R
import com.example.ra1_somativa.ui.fragment.CheckboxFragment

class MealDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealDetailsBinding
    private val viewModel: MealViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMealDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealId = intent.getStringExtra("mealId")

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

        binding.floatingActionButton.setOnClickListener {
            finish()
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