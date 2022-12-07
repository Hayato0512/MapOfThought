package com.hk.mapofthoughts2.feature_note.presentation.MoreInfoPage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage.AddNoteViewModel

@Composable
fun MoreInfoScreen(
navController: NavController,
viewModel:AddNoteViewModel = hiltViewModel()
){
    val title:String = viewModel.titleState.value
    val content:String = viewModel.contentState.value
    val noteId:Int? = viewModel.currentNoteId
    Column{

        Text(
            text = "MoreInfoPage Launch Successful"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "will show the note id: ${noteId}, Title : ${title} Content ${content}"
        )
    }
}