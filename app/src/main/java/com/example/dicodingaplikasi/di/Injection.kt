package com.example.dicodingaplikasi.di

import android.content.Context
import com.example.dicodingaplikasi.data.local.repository.EventRepository
import com.example.dicodingaplikasi.data.local.room.EventDatabase
import com.example.dicodingaplikasi.data.remote.retrofit.ApiConfig
import com.example.dicodingaplikasi.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventDatabase.getInstance(context)
        val dao = database.eventDao()
        val appExecutors = AppExecutors()
        return EventRepository.getInstance(apiService, dao, appExecutors)
    }
}