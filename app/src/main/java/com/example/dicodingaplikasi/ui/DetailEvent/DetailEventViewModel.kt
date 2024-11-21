package com.example.dicodingaplikasi.ui.DetailEvent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingaplikasi.data.local.entity.EventEntity
import com.example.dicodingaplikasi.data.local.repository.EventRepository
import kotlinx.coroutines.launch

class DetailEventViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val _favoriteEvent = MutableLiveData<EventEntity?>()
    val favoriteEvent: LiveData<EventEntity?> = _favoriteEvent


    fun addEvent(event: EventEntity) {
        viewModelScope.launch {
            eventRepository.addEvent(event)
        }
    }

    fun deleteEvent(event: EventEntity) {
        viewModelScope.launch {
            eventRepository.deleteEvent(event)
        }
    }

    fun getEvent(id: Int) {
        viewModelScope.launch {
            _favoriteEvent.value = eventRepository.getEvent(id)
        }
    }
}