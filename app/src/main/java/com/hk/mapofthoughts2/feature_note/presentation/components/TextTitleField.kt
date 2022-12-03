package com.hk.mapofthoughts2.feature_note.presentation.components

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hk.mapofthoughts2.feature_note.presentation.NotesPage.NoteViewModel

@Composable
fun TextTitleField(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel(),
    onValueChange: (String)-> Unit,

    ){
BasicTextField(value = "????KKwK", onValueChange = onValueChange)
}