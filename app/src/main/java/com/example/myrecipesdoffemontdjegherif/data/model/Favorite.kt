package com.example.myrecipesdoffemontdjegherif.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myrecipesdoffemontdjegherif.data.db.FavoriteDAO
import com.example.myrecipesdoffemontdjegherif.data.db.FavoriteEntity
import kotlinx.coroutines.launch


class FavoriteViewModel(private val favoriteDAO: FavoriteDAO) : ViewModel() {
    private val _favorites = MutableLiveData<MutableMap<Int, Boolean>>()
    val isFavorite: LiveData<MutableMap<Int, Boolean>> get() = _favorites

    init {
        favoriteDAO.getAllFavorites().asLiveData().observeForever { list ->
            val map = mutableMapOf<Int, Boolean>()
            list.forEach { fav ->
                map[fav.recipesId] = fav.isFavorite
            }
            _favorites.value = map
        }
    }

    fun insert(favoriteEntity: FavoriteEntity) = viewModelScope.launch { favoriteDAO.insert(favoriteEntity)}

    fun updateFavorites(newMap: MutableMap<Int, Boolean>) {
        _favorites.value = newMap
    }

    fun delete(recipesId: Int) = viewModelScope.launch {
        favoriteDAO.deleteById(recipesId)
    }

}

class FavoriteViewModelFactory(private val favoriteDAO: FavoriteDAO) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(favoriteDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}