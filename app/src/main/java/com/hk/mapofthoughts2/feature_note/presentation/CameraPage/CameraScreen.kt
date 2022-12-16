package com.hk.mapofthoughts2.feature_note.presentation.CameraPage

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hk.mapofthoughts2.feature_note.presentation.Camera
import java.io.File

@Composable
fun CameraScreen(
    navController: NavController,
    outputDirectory: File,
){
   Camera(navController, outputDirectory,onMediaCaptured = { url -> })
}