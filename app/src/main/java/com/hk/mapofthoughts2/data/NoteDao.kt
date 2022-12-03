package com.hk.mapofthoughts2.data

import androidx.room.*
import com.hk.mapofthoughts2.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getNotes():
            Flow<List<Note>>

   @Query("SELECT * FROM  note WHERE id=:id")
    fun getNoteById (id:Int):Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note:Note)

    @Delete
    fun deleteNote(note:Note)


}