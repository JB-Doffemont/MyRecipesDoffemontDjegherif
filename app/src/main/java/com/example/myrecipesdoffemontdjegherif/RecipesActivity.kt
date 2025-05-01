package com.example.myrecipesdoffemontdjegherif

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrecipesdoffemontdjegherif.data.model.MealResponse
import com.example.myrecipesdoffemontdjegherif.data.remote.ApiClient
import com.example.myrecipesdoffemontdjegherif.databinding.ActivityRecipesBinding
import com.example.myrecipesdoffemontdjegherif.ui.adapter.MealAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Récupère le nom de la catégorie depuis l’intent
        val categoryName = intent.getStringExtra("category_name")

        // Définit la Toolbar comme action bar
        setSupportActionBar(binding.toolbarRecipesTitle)

        // Définit le titre dynamiquement
        supportActionBar?.title = "$categoryName Recipes"

        ApiClient.apiService.getMealsByCategory(categoryName ?: "").enqueue(object :
            Callback<MealResponse> {
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals ?: emptyList()
                    binding.recyclerViewMeals.layoutManager = LinearLayoutManager(this@RecipesActivity)
                    binding.recyclerViewMeals.adapter = MealAdapter(meals)
                } else {
                    Log.e("API", "Erreur HTTP : ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Log.e("API", "Erreur réseau : ${t.message}")
            }
        })


    }
}