package com.hk.mapofthoughts2.feature_note.presentation.CameraPage

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage.AddNoteViewModel
import com.hk.mapofthoughts2.feature_note.presentation.Camera
import java.io.File
import javax.inject.Inject

@Composable
fun CameraScreen (
    navController: NavController,
    outputDirectory: File,
    addNoteViewModel: AddNoteViewModel
){
   Camera(navController, outputDirectory,onMediaCaptured = { url -> }, addNoteViewModel)

}