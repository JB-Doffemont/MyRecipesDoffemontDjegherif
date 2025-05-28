package com.example.myrecipesdoffemontdjegherif.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavoriteEntity(
    @PrimaryKey val recipesId: Int,
    val title: String,
    val imageUrl: String,
    val isFavorite: Boolean = true
)
