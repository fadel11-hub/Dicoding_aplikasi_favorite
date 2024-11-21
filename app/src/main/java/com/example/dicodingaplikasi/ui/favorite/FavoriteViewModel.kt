package com.example.dicodingaplikasi.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingaplikasi.data.local.entity.EventEntity
import com.example.dicodingaplikasi.data.local.repository.EventRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val eventRepository: EventRepository) : ViewModel() {


    fun getHeadlineFavorite() = eventRepository.getHeadEvent()


    fun getFavorite() = eventRepository.getFavoriteEvent()


    fun saveFavorite(event: EventEntity){
            eventRepository.setFavoriteEvent(event, true)
    }

    fun deleteFavorite(event: EventEntity){
            eventRepository.setFavoriteEvent(event, false)
    }

    fun getFavoriteEvents() = eventRepository.getEvents()

}