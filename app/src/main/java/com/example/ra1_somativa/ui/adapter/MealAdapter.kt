package com.example.ra1_somativa.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ra1_somativa.databinding.CustomGridLayoutBinding
import com.example.ra1_somativa.feature.data.model.Meal
import com.example.ra1_somativa.R
import coil.load

class MealAdapter (
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MealViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(meal: Meal)
    }

    private var listMeal: List<Meal> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = CustomGridLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    return MealViewHolder(binding, listener)
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
    private val binding: CustomGridLayoutBinding,
    private val listener: MealAdapter.OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind (meal: Meal) {
        binding.apply {
            mealName.text = meal.strMeal
            mealImage.load(meal.strMealThumb) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                error(com.google.android.material.R.drawable.mtrl_ic_error)
            }

            itemView.setOnClickListener {
                listener.onItemClick(meal)
            }
        }
    }
}