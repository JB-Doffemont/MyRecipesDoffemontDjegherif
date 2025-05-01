package com.example.myrecipesdoffemontdjegherif.data.remote

import com.example.myrecipesdoffemontdjegherif.data.model.CategoryResponse
import retrofit2.Call
import retrofit2.http.GET

interface TheMealApi {
    @GET("categories.php")
    fun getCategories(): Call<CategoryResponse>
}