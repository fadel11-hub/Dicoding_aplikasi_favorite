package com.example.dicodingaplikasi.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*

import com.example.dicodingaplikasi.data.local.entity.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM events ")
    fun getFavorite(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM events")
    fun getAllEvents(): LiveData<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend
    fun insertEvent(events: List<EventEntity>)

    @Update
//    suspend
    fun updateFavorite(event: EventEntity)

    @Delete
//    suspend
    fun delete(event: EventEntity)

    @Query("DELETE FROM events")
    fun deleteAll()

//    @Query("SELECT EXISTS(SELECT * FROM events WHERE id = :id AND favorite = 1)")
//    fun isFavorite(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEvent(event: EventEntity)

    @Delete
    suspend fun deleteEvent(event: EventEntity)

    @Query("SELECT * FROM events")
    fun getEvents(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM events where id = :id LIMIT 1")
    suspend fun getEvent(id: Int): EventEntity?
}
