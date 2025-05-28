package com.example.myrecipesdoffemontdjegherif.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 3, exportSchema = false)
abstract class MyRecipesDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDAO

    companion object {
        private val LOGTAG = MyRecipesDatabase::class.simpleName

        @Volatile
        private var INSTANCE: MyRecipesDatabase? = null

        fun getDatabase(context: Context): MyRecipesDatabase {
            Log.d(LOGTAG, "getDatabase()")

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyRecipesDatabase::class.java,
                    "my_recipes_database"
                )
                    .fallbackToDestructiveMigration(true) // ⚠️ Pour supprimer et recréer la DB si changement
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
