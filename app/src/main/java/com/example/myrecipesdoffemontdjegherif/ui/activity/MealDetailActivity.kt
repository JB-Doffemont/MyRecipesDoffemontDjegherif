package com.example.myrecipesdoffemontdjegherif.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myrecipesdoffemontdjegherif.MyRecipesApplication
import com.example.myrecipesdoffemontdjegherif.R
import com.example.myrecipesdoffemontdjegherif.data.db.FavoriteEntity
import com.example.myrecipesdoffemontdjegherif.data.model.Meal
import com.example.myrecipesdoffemontdjegherif.data.model.MealResponse
import com.example.myrecipesdoffemontdjegherif.data.remote.ApiClient
import com.example.myrecipesdoffemontdjegherif.databinding.ActivityMealDetailBinding
import com.example.myrecipesdoffemontdjegherif.data.model.FavoriteViewModel
import com.example.myrecipesdoffemontdjegherif.data.model.FavoriteViewModelFactory
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailActivity : AppCompatActivity() {

    private val LOGTAG = MealDetailActivity::class.simpleName
    private lateinit var binding: ActivityMealDetailBinding

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory((application as MyRecipesApplication).favoriteDAO)
    }

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
                            bindMealDetails(meal)
                        }
                    } else {
                        Log.e(LOGTAG, "Erreur HTTP : ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                    Log.e(LOGTAG, "Erreur réseau : ${t.message}")
                }
            })
        }
    }

    private fun bindMealDetails(meal: Meal) {
        binding.textMealName.text = meal.strMeal
        binding.textMealCategoryOrigin.text = "${meal.strCategory} - ${meal.strArea}"
        binding.textMealInstructions.text = meal.strInstructions

        Picasso.get().load(meal.strMealThumb).into(binding.imageMealDetail)

        val ingredients = getIngredientList(meal)
        binding.textMealIngredients.text = ingredients.joinToString("\n")

        val mealIdInt = meal.idMeal?.toIntOrNull() ?: return
        val imageUrl = meal.strMealThumb
        val title = meal.strMeal

        if (imageUrl != null) {
            setupFavoriteButton(mealIdInt, imageUrl, title)
        }
    }

    private fun setupFavoriteButton(mealId: Int, imageUrl: String, title: String) {
        val colorNormal = getColor(R.color.ic_favorite_normal)
        val colorSelected = getColor(R.color.ic_favorite_selected)
        val textAdd = getString(R.string.favorite_add)
        val textDel = getString(R.string.favorite_del)

        favoriteViewModel.isFavorite.observe(this) { map ->
            val isFav = map[mealId] ?: false
            binding.favoriteIv.setColorFilter(if (isFav) colorSelected else colorNormal)
            binding.favoriteTV.text = if (isFav) textDel else textAdd
        }

        binding.favoriteIv.setOnClickListener {
            val isFavNow = !(favoriteViewModel.isFavorite.value?.get(mealId) ?: false)
            val updatedMap = favoriteViewModel.isFavorite.value?.toMutableMap() ?: mutableMapOf()
            updatedMap[mealId] = isFavNow
            favoriteViewModel.updateFavorites(updatedMap)

            if (isFavNow) {
                val favoriteEntity = FavoriteEntity(mealId, title, imageUrl)
                favoriteViewModel.insert(favoriteEntity)
            } else {
                favoriteViewModel.delete(mealId)
            }

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
