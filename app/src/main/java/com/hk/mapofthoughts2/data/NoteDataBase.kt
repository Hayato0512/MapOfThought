package com.hk.mapofthoughts2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hk.mapofthoughts2.domain.model.Note

@Database(
    entities =[Note::class] ,
    version = 2
)
abstract class NoteDataBase: RoomDatabase() {

    abstract val noteDao:NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}