package com.hk.mapofthoughts2.feature_note.presentation.components

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage.AddNoteViewModel
import com.hk.mapofthoughts2.feature_note.presentation.NotesPage.NoteViewModel

@Composable
fun TextTitleField(
    navController: NavController,
    onValueChange: (String)-> Unit,
currentText:String,
    modifier: Modifier
    ){

BasicTextField(value = currentText, onValueChange = onValueChange)
}