package com.hk.mapofthoughts2.domain.repository

import com.hk.mapofthoughts2.data.NoteDao
import com.hk.mapofthoughts2.domain.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) :NoteRepository{
    //Where do I get Dao
    override fun getNotes(): Flow<List<Note>>{
    return dao.getNotes();
    }

    override suspend fun getNoteById(id:Int): Note?{
    return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note){
        println("debug: now in repo. insertNote")
    return dao.insertNote(note)

    }

    override suspend fun deleteNode(note: Note){
    return dao.deleteNote(note)
    }
}