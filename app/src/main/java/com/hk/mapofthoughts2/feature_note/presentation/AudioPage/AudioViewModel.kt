package com.hk.mapofthoughts2.feature_note.presentation.AudioPage

import android.content.Context
import android.media.MediaRecorder
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hk.mapofthoughts2.feature_note.presentation.AudioPage.entities.Recording
import com.hk.mapofthoughts2.feature_note.presentation.AudioPage.entities.generateRecordingName
import com.hk.mapofthoughts2.feature_note.presentation.MoreInfoPage.MoreInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
): ViewModel(){

   var recorder :MediaRecorder?= null
    //MutableStateFlow
    var recordings = MutableStateFlow<List<Recording>>(emptyList())
    var currentRecording = MutableStateFlow<Recording?>(null)
    val recordingState = MutableStateFlow(false)
    val moreInfoRepository = MoreInfoRepository()

    var folderPath: String = ""
    init{
        readRecordings()
    }

    fun readRecordings(){
        //access to MoreInfoRepository
        viewModelScope.launch(Dispatchers.IO){
            recordings.emit(moreInfoRepository.getRecordings(folderPath))
        }
    }

    fun startRecording(){

        val fileName = generateRecordingName(folderPath)

        recorder = MediaRecorder().apply{
           setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioEncodingBitRate(16 * 44100)
            setAudioSamplingRate(44100)
        }

        try {
           recorder?.prepare()
            println("debug: YES!!!!!! successfully prepare the recorder.")
            recorder?.start()
            println("debug: YES!!!!!! successfully started the recorder.")
            viewModelScope.launch{
               recordingState.emit(true)
                println("debug: YES!!!!!! successfully emit true recordingState.")
            }
            println("debug: YES!!!!!! successfully Started Recording.")
        }catch(e:Exception){
            println("debug: ERROR CATHCED: ${e.localizedMessage}")
            println("debug: No!!! FAILED to start recording")
            viewModelScope.launch {
                recordingState.emit(false)
            }
        }
    }

    fun stopRecording(){
        try {
            recorder?.apply{
                stop()
                reset()
                release()
            }
            recorder = null
            viewModelScope.launch{
                recordingState.emit(false)
            }
            println("debug:Yes!! successfully stop the recording. ")
        }catch(e:Exception){
            println("debug: No!!! FAILED to stop recording")
          println("debug: error ${e.localizedMessage}")
        }

        readRecordings()
    }
}