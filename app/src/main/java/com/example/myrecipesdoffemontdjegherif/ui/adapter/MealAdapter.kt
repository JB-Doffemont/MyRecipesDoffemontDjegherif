package com.example.myrecipesdoffemontdjegherif.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipesdoffemontdjegherif.ui.activity.MealDetailActivity
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

        holder.binding.root.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MealDetailActivity::class.java)

            // On passe l'id du plat (ou son nom si tu préfères)
            intent.putExtra("meal_id", meal.idMeal)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = meals.size
}