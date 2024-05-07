package com.example.ra1_somativa.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ra1_somativa.databinding.CustomGridLayoutBinding
import com.example.ra1_somativa.feature.data.model.Meal
import com.example.ra1_somativa.R
import coil.load

class MealAdapter : RecyclerView.Adapter<MealViewHolder>() {

    private var listMeal: List<Meal> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
            val binding = CustomGridLayoutBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun getItemCount(): Int = listMeal.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(mealList: List<Meal>) {
        this.listMeal = mealList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = listMeal[position]
        holder.bind(meal)
    }

}

class MealViewHolder (
    private val binding: CustomGridLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind (meal: Meal) {
        binding.apply {
            mealName.text = meal.strMeal
            // Carrega a imagem da refeição utilizando o Coil
            mealImage.load(meal.strMealThumb) {
                crossfade(true) // Aplica um efeito de transição suave ao carregar a imagem
                placeholder(R.drawable.ic_launcher_foreground) // Exibe um placeholder enquanto a imagem está sendo carregada
                error(com.google.android.material.R.drawable.mtrl_ic_error) // Exibe uma imagem de erro em caso de falha no carregamento
            }
        }
    }
}