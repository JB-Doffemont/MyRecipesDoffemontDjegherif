package com.example.myrecipesdoffemontdjegherif.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrecipesdoffemontdjegherif.MyRecipesApplication
import com.example.myrecipesdoffemontdjegherif.R
import com.example.myrecipesdoffemontdjegherif.databinding.ActivityFavoriteBinding
import com.example.myrecipesdoffemontdjegherif.ui.adapter.FavoriteListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var adapter: FavoriteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Setup de la Toolbar comme ActionBar
        setSupportActionBar(binding.toolbarFavorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 2. Initialisation du DrawerToggle
        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.favoriteDrawerLayout,
            binding.toolbarFavorite,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.favoriteDrawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        // 3. Gestion du clic sur les items du menu
        binding.navViewFavorite.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_main -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_favorites -> {
                    true
                }
                else -> false
            }.also {
                binding.favoriteDrawerLayout.closeDrawers()
            }
        }

        // 4. Configuration du RecyclerView
        adapter = FavoriteListAdapter { fav ->
            val intent = Intent(this, MealDetailActivity::class.java)
            intent.putExtra("meal_id", fav.recipesId.toString())
            startActivity(intent)
        }
        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFavorites.adapter = adapter

        // 5. Chargement des favoris depuis la BDD
        lifecycleScope.launch {
            (application as MyRecipesApplication).favoriteDAO.getOnlyFavorites()
                .collectLatest { list ->
                    adapter.submitList(list)
                }
        }
    }

    // Nécessaire pour que le DrawerToggle fonctionne au clic sur l'icône burger
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }
}
