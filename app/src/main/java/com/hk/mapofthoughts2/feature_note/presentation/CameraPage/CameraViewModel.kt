package com.hk.mapofthoughts2.feature_note.presentation.CameraPage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hk.mapofthoughts2.domain.model.Note
import com.hk.mapofthoughts2.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private var _state = mutableStateOf(String())
    val pathState = _state

    init{
        savedStateHandle.get<String>("path")?.let{
                path->
            if(path!=""){
               _state.value = path
            }
        }
    }
}
