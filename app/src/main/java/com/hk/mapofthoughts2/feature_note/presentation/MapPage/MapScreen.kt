package com.hk.mapofthoughts2.feature_note.presentation.MapPage

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hk.mapofthoughts2.feature_note.presentation.NotesPage.NoteViewModel

@Composable
fun MapScreen(
    navController: NavController,
    viewModel:NoteViewModel = hiltViewModel()
    ){

    Text(text="mapScreen successful")

}