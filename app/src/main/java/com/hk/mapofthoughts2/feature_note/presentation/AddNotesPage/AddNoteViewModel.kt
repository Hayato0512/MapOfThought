package com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.hk.mapofthoughts2.domain.model.Note
import com.hk.mapofthoughts2.domain.repository.NoteRepository
import com.hk.mapofthoughts2.feature_note.presentation.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(){
//    var fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient()
    private var _state = mutableStateOf(String())
    val titleState = _state
    private var _contentState = mutableStateOf(String())
    val contentState = _contentState
    var currentNoteId: Int? = null

    init{
        savedStateHandle.get<Int>("noteId")?.let{
            noteId->
        if(noteId!=-1){
            viewModelScope.launch{
               noteRepository.getNoteById(noteId)?.also{
                   note->
                   currentNoteId = note.id
                   _state.value = note.title
                   _contentState.value = note.content
               }
            }
        }
        }
    }
    suspend fun addNote(note: Note){
        noteRepository.insertNote(note)
    }
}