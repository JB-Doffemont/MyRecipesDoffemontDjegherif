//package com.example.myrecipesdoffemontdjegherif.ui.activity
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.myrecipesdoffemontdjegherif.MyRecipesApplication
//import com.example.myrecipesdoffemontdjegherif.R
//import com.example.myrecipesdoffemontdjegherif.databinding.ActivityFavoriteBinding
//import com.example.myrecipesdoffemontdjegherif.ui.adapter.FavoriteListAdapter
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.flow.collectLatest
//
//class FavoriteActivity : BaseDrawerActivity() {
//
//    private lateinit var binding: ActivityFavoriteBinding
//    private lateinit var adapter: FavoriteListAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        layoutInflater.inflate(R.layout.activity_favorite, drawerBinding.contentFrame, true)
//        binding = ActivityFavoriteBinding.bind(drawerBinding.contentFrame.getChildAt(0))
//
//        supportActionBar?.title = "Mes favoris"
//
//        adapter = FavoriteListAdapter { favorite ->
//            val intent = Intent(this, MealDetailActivity::class.java)
//            intent.putExtra("meal_id", favorite.recipesId.toString())
//            startActivity(intent)
//        }
//
//        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(this)
//        binding.recyclerViewFavorites.adapter = adapter
//
//        lifecycleScope.launch {
//            (application as MyRecipesApplication).favoriteDAO.getOnlyFavorites().collectLatest { list ->
//                adapter.submitList(list)
//            }
//        }
//    }
//}
