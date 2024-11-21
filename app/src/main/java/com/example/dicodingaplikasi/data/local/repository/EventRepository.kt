package com.example.dicodingaplikasi.data.local.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.dicodingaplikasi.data.local.entity.EventEntity
import com.example.dicodingaplikasi.data.local.room.EventDao
import com.example.dicodingaplikasi.data.remote.response.Event
import com.example.dicodingaplikasi.data.remote.response.EventResponse
import com.example.dicodingaplikasi.data.remote.response.ListEventsItem
import com.example.dicodingaplikasi.data.remote.retrofit.ApiService
import com.example.dicodingaplikasi.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository private constructor(
    private val eventsDao: EventDao,
    private val appExecutors: AppExecutors,
    private val apiService: ApiService
) {
    private val result = MediatorLiveData<Result<List<EventEntity>>>()

    fun getHeadEvent(): LiveData<Result<List<EventEntity>>> {
        result.value = Result.Loading
        val client = apiService.getUpComing("1")
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val program = response.body()?.listEvents
                    val eventList = ArrayList<EventEntity>()

                    appExecutors.diskIO.execute {
                        program?.forEach { event ->
                            val eventEntity = mapToEventEntity(event)
                            eventList.add(eventEntity)
                        }
                        eventsDao.deleteAll()
                        eventsDao.insertEvent(eventList)
                    }
                } else {
                    result.postValue(Result.Error("Failed to fetch data from API"))
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                result.postValue(Result.Error(t.message.toString()))
            }
        })

        val localData = eventsDao.getAllEvents()
        result.addSource(localData) { newData ->
            result.postValue(Result.Success(newData))
        }
        return result
    }

    private fun mapToEventEntity(event: ListEventsItem): EventEntity {
        return EventEntity(
            id = event.id, // Konversi id ke String
            name = event.name,
            mediaCover = event.mediaCover,
            summary = event.summary,
            description = event.description,
            ownerName = event.ownerName,
            cityName = event.cityName,
            quota = event.quota,
            link = event.link,
            beginTime = event.beginTime,
            endTime = event.endTime,
            category = event.category,
            registrants = event.registrants
        )
    }

    fun getFavoriteEvent(): LiveData<List<EventEntity>> {
        return eventsDao.getFavorite()
    }

    fun setFavoriteEvent(event: EventEntity, favoriteState: Boolean) {
        appExecutors.diskIO.execute {
            // Implementasi favorite jika diperlukan
//            event.isFavorite = favoriteState
            // Anda mungkin perlu menambahkan properti favorite di EntityEvent
            eventsDao.updateFavorite(event)
        }
    }

    suspend fun addEvent(event: EventEntity) = eventsDao.addEvent(event)

    suspend fun deleteEvent(event: EventEntity) = eventsDao.deleteEvent(event)

    fun getEvents(): LiveData<List<EventEntity>> = eventsDao.getEvents()

    suspend fun getEvent(id: Int): EventEntity? = eventsDao.getEvent(id = id)

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            eventsDao: EventDao,
            appExecutors: AppExecutors
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(eventsDao, appExecutors, apiService)
            }.also { instance = it }
    }
}