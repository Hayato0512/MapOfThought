package com.hk.mapofthoughts2.feature_note.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hk.mapofthoughts2.feature_note.presentation.NotesPage.NoteViewModel

@Composable
fun AddNoteScreen(
    navController:NavController,
    viewModel: NoteViewModel = hiltViewModel()
){

    Box{
      Text(
          text = "Add Note Screen",
      modifier = Modifier.fillMaxWidth()
      )
        TextTitleField(navController = navController, onValueChange = {
            println("debug: i'm addNoteScreen and calling TextTitleField")
        })
    }

}