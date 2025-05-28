package com.example.myrecipesdoffemontdjegherif

import android.app.Application
import com.example.myrecipesdoffemontdjegherif.data.db.MyRecipesDatabase

class MyRecipesApplication : Application() {
    val database by lazy { MyRecipesDatabase.getDatabase(this) }
    val favoriteDAO by lazy { database.favoriteDao() }
}