package com.hk.mapofthoughts2.feature_note.presentation.MoreInfoPage

import android.app.Activity
import android.app.Application
import android.media.MediaRecorder
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hk.mapofthoughts2.domain.repository.NoteRepository
import com.hk.mapofthoughts2.feature_note.presentation.AudioPage.entities.generateRecordingName
import com.hk.mapofthoughts2.feature_note.presentation.MainActivity
import dagger.Provides
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.net.URI
import javax.inject.Inject

class MoreInfoViewModel (
    activity: Activity,
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
    private val audioPlayer: AudioPlayer = AudioPlayer(activity)
    var recorder: MediaRecorder? = null
    //MutableStateFlow
    val recordingState = MutableStateFlow(false)

    var folderPath: String = ""
    var currentPath =
        "/storage/emulated/0/Android/media/com.hk.mapofthoughts2/MapOfThoughts2/19122022-140908.m4a"
    var uriToPass = Uri.fromFile(
        File(currentPath)
    )

    val middlePlayer = MutableStateFlow(MiddlePlayer())
    val mHandler = Handler(Looper.getMainLooper())


    fun initMediaPlayer(uri: Uri) {

        viewModelScope.launch {
            audioPlayer.prepareMediaPlayer(uriToPass)
            //now, the path is hardcorded.
            middlePlayer.emit(
                middlePlayer.value.copy(
                    duration = audioPlayer.mediaPlayer.duration,
                    currentPosition = audioPlayer.mediaPlayer.currentPosition,
                    isPlaying = false
                )
            )
        }
        audioPlayer.mediaPlayer.setOnCompletionListener {
            viewModelScope.launch {
                middlePlayer.emit(
                    middlePlayer.value.copy(
                        isPlaying = false, currentPosition = audioPlayer.mediaPlayer.duration
                    )
                )
            }
        }
    }

    fun playMedia() {
        viewModelScope.launch {
            audioPlayer.mediaPlayer.start()

            middlePlayer.emit(
                middlePlayer.value.copy(
                    isPlaying = true
                )
            )
            println("debug: YEs!! successfully play MEdia")
        }
    }
        fun pauseMedia() {
            viewModelScope.launch {
                audioPlayer.mediaPlayer.pause()
                mHandler.removeCallbacksAndMessages(null)
                middlePlayer.emit(
                    middlePlayer.value.copy(
                        isPlaying = false
                    )
                )
                println("debug: YEs!! successfully pause MEdia")
            }


        mHandler.postDelayed(object : Runnable {

            override fun run() {
                if (middlePlayer.value.isPlaying) {
                    mHandler.postDelayed(this, 1000)
                    updateCurrentPosition()
                }
            }
        }, 0)
    }

    private fun updateCurrentPosition() {
        viewModelScope.launch {
            middlePlayer.emit(
                middlePlayer.value.copy(
                    currentPosition = audioPlayer.mediaPlayer.currentPosition

                )
            )
        }


        fun startRecording() {

            val fileName = generateRecordingName(folderPath)

            recorder = MediaRecorder().apply {
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
                viewModelScope.launch {
                    recordingState.emit(true)
                    println("debug: YES!!!!!! successfully emit true recordingState.")
                }
                println("debug: YES!!!!!! successfully Started Recording.")
            } catch (e: Exception) {
                println("debug: ERROR CATHCED: ${e.localizedMessage}")
                println("debug: No!!! FAILED to start recording")
                viewModelScope.launch {
                    recordingState.emit(false)
                }
            }
        }

        fun stopRecording() {
            try {
                recorder?.apply {
                    stop()
                    reset()
                    release()
                }
                recorder = null
                viewModelScope.launch {
                    recordingState.emit(false)
                }
                println("debug:Yes!! successfully stop the recording. ")
            } catch (e: Exception) {
                println("debug: No!!! FAILED to stop recording")
                println("debug: error ${e.localizedMessage}")
            }

//        readRecordings()
        }

    }
    data class MiddlePlayer(
        val isPlaying: Boolean = false,
        val currentPosition: Int = 0,
        val duration: Int = 10
    )
}