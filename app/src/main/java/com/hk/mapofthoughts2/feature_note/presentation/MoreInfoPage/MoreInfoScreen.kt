package com.hk.mapofthoughts2.feature_note.presentation.MoreInfoPage

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage.AddNoteViewModel
import com.hk.mapofthoughts2.feature_note.presentation.AudioPage.AudioViewModel
import com.hk.mapofthoughts2.feature_note.presentation.AudioPage.entities.Recording
import com.hk.mapofthoughts2.feature_note.presentation.MainActivity
import dagger.Lazy
import retrofit2.http.Url
import java.io.File

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MoreInfoScreen(
navController: NavController,
acitivity:MainActivity,
moreInfoViewModel : MoreInfoViewModel,

moreInfoDetailViewModel : MoreInfoDetailViewModel,
//viewModel:AddNoteViewModel = hiltViewModel(),
        audioViewModel:AudioViewModel = hiltViewModel(),
){
    val title:String = moreInfoDetailViewModel.titleState.value
    val content:String = moreInfoDetailViewModel.contentState.value
    val noteId:Int? = moreInfoDetailViewModel.currentNoteId
    val imagePathName :String = moreInfoDetailViewModel.imageNameState.value
    val audioPathName :String = moreInfoDetailViewModel.audioNameState.value
    println("debug: MoreInfoScreen. andioViewModel.recording.value is ${audioViewModel.recordings }")
    var recordings :List<Recording?> = audioViewModel.recordings.value
    val playingState by moreInfoViewModel.middlePlayer.collectAsState()

   moreInfoViewModel.initMediaPlayer(Uri.fromFile(File("")))
    Column{

        Text(
            text = "MoreInfoPage Launch Successful"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "will show the note id: ${noteId}, Title : ${title} Content ${content}"
        )
        Text(
            text = "imagePathName ${imagePathName}"
        )
        Text(
            text = "audioPathName ${audioPathName}"
        )
        if(recordings.size==0){
            Text(
                text="the recordings is empty. "
            )
        }
        Button(
           onClick = {
               moreInfoViewModel.playMedia()
           }
        ){
            Text(
                text="Play the audio"
            )
        }
        Button(
            onClick = {
                moreInfoViewModel.pauseMedia()
            }

        ){
            Text(
                text="Stop the audio"
            )
        }
        Button(
            onClick = {
                moreInfoDetailViewModel.isMoreInfoPage.value = false
            }

        ){
            Text(
                text="go back to the notes page"
            )
        }
        LazyColumn{
            itemsIndexed(recordings){index, recording->
               Text(
                  text = "index ${index.toString()}"
               )
                Text(
                    text = "recording date, ${recording?.readableDate}  path:${recording?.path} "
                )
                Box(modifier = Modifier.height(40.dp))

            }
        }
    }
}