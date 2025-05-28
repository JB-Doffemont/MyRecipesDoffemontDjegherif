package com.example.myrecipesdoffemontdjegherif.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrecipesdoffemontdjegherif.R
import com.example.myrecipesdoffemontdjegherif.data.model.MealResponse
import com.example.myrecipesdoffemontdjegherif.data.remote.ApiClient
import com.example.myrecipesdoffemontdjegherif.databinding.ActivityRecipesBinding
import com.example.myrecipesdoffemontdjegherif.ui.adapter.MealAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class RecipesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipesBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Toolbar
        setSupportActionBar(binding.toolbarRecipesTitle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 2. DrawerToggle
        val drawerLayout = findViewById<DrawerLayout>(R.id.recipes_drawer_layout)
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbarRecipesTitle,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // 3. Listener du menu
        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_main -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                R.id.nav_favorites -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                }
            }
            drawerLayout.closeDrawers()
            true
        }

        // --- Ton ancien code de chargement des recettes ---
        val categoryName = intent.getStringExtra("category_name")
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

    // Nécessaire pour que le toggle gère le clic sur l'icône burger
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item)) true
        else super.onOptionsItemSelected(item)
    }
}
