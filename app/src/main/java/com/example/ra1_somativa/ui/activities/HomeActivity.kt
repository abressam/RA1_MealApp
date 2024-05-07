package com.example.ra1_somativa.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ra1_somativa.R
import com.example.ra1_somativa.databinding.ActivityHomeBinding
import com.example.ra1_somativa.feature.presentation.MealViewModel
import com.example.ra1_somativa.ui.adapter.MealAdapter

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: MealViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recycleView
        val adapter = MealAdapter()

        val layoutManager = GridLayoutManager(this, 2)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        viewModel.mealListInfo.observe(this) { meals ->
            adapter.updateList(meals)
        }

        viewModel.fetchData()

    }
}