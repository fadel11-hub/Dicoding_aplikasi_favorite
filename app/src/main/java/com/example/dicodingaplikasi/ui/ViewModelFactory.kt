package com.example.dicodingaplikasi.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingaplikasi.data.local.repository.EventRepository
import com.example.dicodingaplikasi.di.Injection
import com.example.dicodingaplikasi.ui.DetailEvent.DetailEventViewModel
import com.example.dicodingaplikasi.ui.favorite.FavoriteViewModel

class ViewModelFactory private constructor(private val favoriteRepository: EventRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favoriteRepository) as T
        } else if(modelClass.isAssignableFrom(DetailEventViewModel::class.java)) {
            return DetailEventViewModel(favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}