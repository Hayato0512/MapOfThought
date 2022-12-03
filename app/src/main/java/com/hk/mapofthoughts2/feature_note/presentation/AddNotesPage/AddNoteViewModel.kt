package com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import com.hk.mapofthoughts2.domain.model.Note
import com.hk.mapofthoughts2.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
): ViewModel(){
    private var _state = mutableStateOf(String())
    val titleState = _state
    private var _contentState = mutableStateOf(String())
    val contentState = _contentState

    suspend fun addNote(note: Note){
        noteRepository.insertNote(note)
    }
}