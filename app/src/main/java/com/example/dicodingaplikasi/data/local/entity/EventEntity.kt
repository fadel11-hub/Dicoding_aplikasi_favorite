package com.example.dicodingaplikasi.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
class EventEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var name: String = "",
    var mediaCover: String? = null,
    var summary: String?,
    var description: String?,
    var ownerName: String?,
    var cityName: String?,
    var quota: Int?,
    var link: String?,
    var beginTime: String?,
    var endTime: String?,
    var category: String?,
    var registrants: Int?
//    @ColumnInfo(name = "isFavorite")
//    var isFavorite: Boolean = false


)