package com.example.myrecipesdoffemontdjegherif.data.model

data class CategoryResponse(
    val categories: List<Category>
)

data class Category(
    val strCategory: String,
    val strCategoryThumb: String
)