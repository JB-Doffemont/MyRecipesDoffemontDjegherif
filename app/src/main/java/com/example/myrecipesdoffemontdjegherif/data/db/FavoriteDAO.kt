package com.example.myrecipesdoffemontdjegherif.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)

    @Query("DELETE FROM favorite_table WHERE recipesId = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM favorite_table")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite_table WHERE isFavorite = 1")
    fun getOnlyFavorites(): Flow<List<FavoriteEntity>>
}
