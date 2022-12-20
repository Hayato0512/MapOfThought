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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
): ViewModel(){
//    var fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient()
    private var _state = mutableStateOf(String())
        private val savedStateHandle= SavedStateHandle()
    val titleState = _state
    private var _contentState = mutableStateOf(String())
    val contentState = _contentState
    private var _imageNameState = mutableStateOf(String())
    val imageNameState = _imageNameState
    private var _audioNameState = mutableStateOf(String())
    val audioNameState = _audioNameState
    var currentNoteId: Int? = null

    var isCameraScreen = mutableStateOf(false)
    var isAudioScreen = mutableStateOf(false)

    var currentImageName = mutableStateOf(String())
    var currentAudio = mutableStateOf(String())


    init{

//        viewModelScope.launch(Dispatchers.IO){
//            println("debug: Hello, now just came into viewModelScope coroutine.")
//            val client = OkHttpClient()
//            println("debug: Hello, just initiated OkHTTPCLIENT")
//            val request = Request.Builder()
//                .url("https://open-weather13.p.rapidapi.com/city/latlon/30.438/-89.1028")
//                .get()
//                .addHeader("X-RapidAPI-Key", "723a040345msh9777641212e2f87p121c09jsn695f86c6a7e8")
//                .addHeader("X-RapidAPI-Host", "open-weather13.p.rapidapi.com")
//                .build()
//
//            val response = client.newCall(request).execute()
////        val jsonString = response.body()?.string()
//            println("debug: hello this is AddNoteViewModel. ")
//        }

        //can someone let this viewmodel about noteId? I am sure.
        //in NotesScreen, get access to AddNoteViewModel.
        //who else using this AddNoteViewModel? we are interested in MoreInfoScreen
        //and then when user clicks one entry, get the id, and then
        //send it to this viewModel as selectedNoteId.
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