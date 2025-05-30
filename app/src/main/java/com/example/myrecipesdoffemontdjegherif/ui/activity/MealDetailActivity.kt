package com.example.myrecipesdoffemontdjegherif.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myrecipesdoffemontdjegherif.data.model.Meal
import com.example.myrecipesdoffemontdjegherif.data.model.MealResponse
import com.example.myrecipesdoffemontdjegherif.data.remote.ApiClient
import com.example.myrecipesdoffemontdjegherif.databinding.ActivityMealDetailBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealId = intent.getStringExtra("meal_id")

        supportActionBar?.title = "Meal #$mealId"

        if (mealId != null) {
            ApiClient.apiService.getMealById(mealId).enqueue(object : Callback<MealResponse> {
                override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                    if (response.isSuccessful) {
                        val meal = response.body()?.meals?.firstOrNull()
                        if (meal != null) {
                            binding.textMealName.text = meal.strMeal
                            binding.textMealCategoryOrigin.text = "${meal.strCategory} - ${meal.strArea}"
                            binding.textMealInstructions.text = meal.strInstructions
                            Picasso.get().load(meal.strMealThumb).into(binding.imageMealDetail)

                            val ingredients = getIngredientList(meal)
                            binding.textMealIngredients.text = ingredients.joinToString("\n")

                            val nutriScore = (0..100).random()
                            binding.nutriScoreView.setScore(nutriScore)

                        }
                    } else {
                        Log.e("DETAIL", "Erreur HTTP : ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                    Log.e("DETAIL", "Erreur réseau : ${t.message}")
                }
            })
        }


    }

    private fun getIngredientList(meal: Meal): List<String> {
        val ingredients = mutableListOf<String>()

        for (i in 1..20) {
            val ingredient = Meal::class.java.getDeclaredField("strIngredient$i")
                .apply { isAccessible = true }
                .get(meal) as? String

            val measure = Meal::class.java.getDeclaredField("strMeasure$i")
                .apply { isAccessible = true }
                .get(meal) as? String

            if (!ingredient.isNullOrBlank() && ingredient != "null") {
                val formatted = if (!measure.isNullOrBlank() && measure != "null") {
                    "$ingredient - $measure"
                } else {
                    ingredient
                }
                ingredients.add(formatted)
            }
        }

        return ingredients
    }

}