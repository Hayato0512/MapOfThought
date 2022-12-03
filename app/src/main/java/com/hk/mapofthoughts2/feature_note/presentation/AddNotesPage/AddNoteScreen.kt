package com.hk.mapofthoughts2.feature_note.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hk.mapofthoughts2.domain.model.Note
import com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage.AddNoteViewModel
import com.hk.mapofthoughts2.feature_note.presentation.NotesPage.NoteViewModel
import com.hk.mapofthoughts2.feature_note.presentation.Screen
import kotlinx.coroutines.launch

@Composable
fun AddNoteScreen(
    navController:NavController,
    viewModel: AddNoteViewModel = hiltViewModel()
){
    val titleState = viewModel.titleState.value
    val contentState = viewModel.contentState.value

    val scope = rememberCoroutineScope()

    Box{
        Column{

            Text(
            text = "Add Note Screen",
            modifier = Modifier.fillMaxWidth()
            )
            TextTitleField(
                navController = navController,
                currentText = titleState,
                onValueChange = {newText->
                   viewModel.titleState.value = newText
                },
                modifier = Modifier
            )
            TextTitleField(
                navController = navController,
                currentText = contentState,
                onValueChange = {newText->
                    viewModel.contentState.value = newText

                },
                modifier = Modifier
            )
            Button(
                onClick = {
                    val noteToInsert = Note(titleState,contentState,"myRoom", "12", "12")
                    scope.launch{
                        viewModel.addNote(noteToInsert)
                    }
                   navController.navigate(Screen.NotesScreen.route)
                }

            ) {
                Text(text="submit")

            }
        }
    }

}