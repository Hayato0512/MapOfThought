package com.hk.mapofthoughts2.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    var title:String,
    var content: String,
    var locationName:String,
    var latitude: String,
    var longitude: String,


    @PrimaryKey var id: Int? = null
)


