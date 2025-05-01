package com.example.myrecipesdoffemontdjegherif

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myrecipesdoffemontdjegherif.data.model.CategoryResponse
import com.example.myrecipesdoffemontdjegherif.data.remote.ApiClient
import com.example.myrecipesdoffemontdjegherif.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ApiClient.apiService.getCategories().enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                if (response.isSuccessful) {
                    val categories = response.body()?.categories ?: emptyList()
                    for (category in categories) {
                        Log.d("API", "Catégorie : ${category.strCategory}")
                    }
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