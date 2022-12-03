package com.hk.mapofthoughts2.domain.repository

import com.hk.mapofthoughts2.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

//    val allNotes:Flow<List<Note>>
    //to get
    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id:Int): Note?

    suspend fun insertNote(note:Note)

    suspend fun deleteNode(note:Note)

    suspend fun deleteFirstElement(id:Int)
}