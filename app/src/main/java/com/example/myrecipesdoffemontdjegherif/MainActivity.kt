package com.example.myrecipesdoffemontdjegherif

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrecipesdoffemontdjegherif.data.model.CategoryResponse
import com.example.myrecipesdoffemontdjegherif.data.remote.ApiClient
import com.example.myrecipesdoffemontdjegherif.databinding.ActivityMainBinding
import com.example.myrecipesdoffemontdjegherif.ui.adapter.CategoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    // ViewBinding pour accéder au layout XML sans findViewById
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisation du binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuration du RecyclerView avec un LinearLayout vertical
        binding.recyclerViewCategories.layoutManager = LinearLayoutManager(this)

        setSupportActionBar(binding.toolbar)

        // Appel asynchrone à l’API pour récupérer les catégories
        ApiClient.apiService.getCategories().enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                if (response.isSuccessful) {
                    val categories = response.body()?.categories ?: emptyList()

                    // On affiche aussi dans les logs pour debug
                    Log.d("API", "Nombre de catégories : ${categories.size}")

                    // On crée l’adapter avec la liste reçue et on l’affecte au RecyclerView
                    val adapter = CategoryAdapter(categories)
                    binding.recyclerViewCategories.adapter = adapter
                } else {
                    Log.e("API", "Erreur HTTP : ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.e("API", "Erreur réseau : ${t.message}")
            }
        })
    }
}