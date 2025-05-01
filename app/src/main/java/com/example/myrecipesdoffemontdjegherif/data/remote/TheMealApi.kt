package com.example.myrecipesdoffemontdjegherif.data.remote

import com.example.myrecipesdoffemontdjegherif.data.model.CategoryResponse
import com.example.myrecipesdoffemontdjegherif.data.model.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMealApi {
    @GET("categories.php")
    fun getCategories(): Call<CategoryResponse>

    @GET("filter.php")
    fun getMealsByCategory(
        @Query("c") category: String
    ): Call<MealResponse>

    @GET("lookup.php")
    fun getMealById(@Query("i") id: String): Call<MealResponse>

}

