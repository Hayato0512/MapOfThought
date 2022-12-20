package com.hk.mapofthoughts2.feature_note.presentation.MoreInfoPage

import android.app.Activity
import android.media.MediaRecorder
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hk.mapofthoughts2.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MoreInfoDetailViewModel@Inject constructor(
    private val noteRepository: NoteRepository,
): ViewModel() {

    private var _titleState = mutableStateOf(String())
    val titleState = _titleState
    private var _contentState = mutableStateOf(String())
    val contentState = _contentState
    private var _imageNameState = mutableStateOf(String())
    val imageNameState = _imageNameState
    private var _audioNameState = mutableStateOf(String())
    val audioNameState = _audioNameState
    var currentNoteId: Int? = null
    var isMoreInfoPage = mutableStateOf(false)



    fun setNoteValues(){
        if(currentNoteId!=null){
            //fetch the data by calling repo, and set values
            viewModelScope.launch{
                noteRepository.getNoteById(currentNoteId!!)?.also{
                        note->
                    currentNoteId = note.id
                    _titleState.value = note.title
                    _contentState.value = note.content
                    _imageNameState.value = note.imageName
                    _audioNameState.value = note.audioName
                }
            }
        }
        else{
            //if noteId is null, do nothing
        }
    }

    }
