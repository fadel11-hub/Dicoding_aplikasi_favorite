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

//    @ColumnInfo(name = "isFavorite")
//    var isFavorite: Boolean = false


)