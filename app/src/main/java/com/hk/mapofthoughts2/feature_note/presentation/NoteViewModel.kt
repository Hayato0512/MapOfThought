package com.hk.mapofthoughts2.feature_note.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hk.mapofthoughts2.domain.model.Note
import com.hk.mapofthoughts2.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
        ): ViewModel(){
//    private val noteRepository =  NoteRepositoryImpl()

    //changed liveData to flow. But, the thing is, can they listen to this??
//    val notes: LiveData<List<Note>> = noteRepository.allNotes
    private var _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var getNotesJob: Job? = null

    init{
   getNotes()
    }

//    private var notes : LiveData<List<Note>> =noteRepository.getNotes().asLiveData() ;
    fun testFuntion(){
        println("debug:::HEUYNEYELJS:LFJKLESJS:LKFEJFK")
    }

    suspend fun onEvent(event:NotesEvent, note: Note){
        if (event ==NotesEvent.addNote){
            coroutineScope {
                println("debug: hey in viewModel, now we send it to repository")
                noteRepository.insertNote(note)
            }
            getNotes()
            return
        }
        else if (event==NotesEvent.deleteNote){
           coroutineScope {
              noteRepository.deleteNode(note)
           }
            return
        }
        else if (event==NotesEvent.getAllNotes){
           coroutineScope{
//               notes = noteRepository.getNotes().asLiveData();
           }
            return
        }
    }
    private fun getNotes(){
        println("debug: now, in get notes before the repositoryGetNote, ${_state.value}")
        getNotesJob = noteRepository.getNotes().onEach { notes->
            _state.value = _state.value.copy(notes = notes)
         }.launchIn(viewModelScope)
        println("debug: now, in get notes After, ${_state.value}")
    }







}