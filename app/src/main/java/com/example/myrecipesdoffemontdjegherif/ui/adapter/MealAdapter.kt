package com.example.myrecipesdoffemontdjegherif.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipesdoffemontdjegherif.data.model.Meal
import com.example.myrecipesdoffemontdjegherif.databinding.ItemMealBinding
import com.squareup.picasso.Picasso

class MealAdapter(private val meals: List<Meal>) :
    RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    class MealViewHolder(val binding: ItemMealBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.binding.textMeal.text = meal.strMeal
        Picasso.get().load(meal.strMealThumb).into(holder.binding.imageMeal)
    }

    override fun getItemCount(): Int = meals.size
}