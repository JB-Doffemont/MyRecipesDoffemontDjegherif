package com.example.myrecipesdoffemontdjegherif.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrecipesdoffemontdjegherif.R
import com.example.myrecipesdoffemontdjegherif.databinding.ActivityMainBinding
import com.example.myrecipesdoffemontdjegherif.data.model.CategoryResponse
import com.example.myrecipesdoffemontdjegherif.data.remote.ApiClient
import com.example.myrecipesdoffemontdjegherif.ui.adapter.CategoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ViewBinding sur activity_main.xml
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1) Toolbar comme ActionBar
        setSupportActionBar(binding.toolbar)

        // 2) DrawerToggle (bouton burger)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.mainDrawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.mainDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // 3) Listener du menu
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_main -> {
                    Toast.makeText(this, "Accueil", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_favorites -> {
                    Toast.makeText(this, "Mes favoris", Toast.LENGTH_SHORT).show()
                    // startActivity(Intent(this, FavoriteActivity::class.java))
                }
            }
            binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // 4) Prépare le RecyclerView
        binding.recyclerViewCategories.layoutManager = LinearLayoutManager(this)
        categoryAdapter = CategoryAdapter(emptyList())
        binding.recyclerViewCategories.adapter = categoryAdapter

        // 5) Charge l'API
        fetchCategories()
    }

    private fun fetchCategories() {
        binding.progressBar.visibility = View.VISIBLE
        ApiClient.apiService.getCategories().enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val list = response.body()?.categories ?: emptyList()
                    categoryAdapter = CategoryAdapter(list)
                    binding.recyclerViewCategories.adapter = categoryAdapter
                } else {
                    showError("Erreur HTTP ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                showError("Échec réseau : ${t.message}")
            }
        })
    }

    private fun showError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
