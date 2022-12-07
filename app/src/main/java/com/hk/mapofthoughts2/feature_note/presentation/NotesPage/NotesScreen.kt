package com.hk.mapofthoughts2.feature_note.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hk.mapofthoughts2.domain.model.Note
import com.hk.mapofthoughts2.feature_note.presentation.NotesPage.NoteViewModel
import com.hk.mapofthoughts2.feature_note.presentation.NotesPage.NotesEvent
import com.hk.mapofthoughts2.feature_note.presentation.Screen
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
   navController: NavController,
   viewModel: NoteViewModel = hiltViewModel()
){
    //we need to make compose state in there.
   val state = viewModel.state.value // ok this is connected to viewModel. Very very very fucking nice.
   val scope = rememberCoroutineScope()
    var count =  remember {mutableStateOf(0)}
    //count.value when use

    Box{
        Column{
//            Column{
           Surface(color=Color.LightGray){
               LazyColumn(
                   modifier = Modifier.fillMaxWidth().weight(1f).height(300.dp)
               ){
                   items(state.notes){note->
                       Surface(
                           color=Color.Green,
                          modifier = Modifier
                              .padding(4.dp)
                              .fillMaxWidth()
                              .border(BorderStroke(2.dp, SolidColor(Color.Red)) )
                              .clickable {
                                  println("debug: you just touched a note: id ${note.id}")
                                  navController.navigate(
                                      Screen.MoreInfoScreen.route +
                                  "?noteId=${note.id}"
                                  )

                              }

                       ){
                           Column{
                               Text(
                                   text = note.title
                               )
                               Row{
                                   Text(
                                       text = note.latitude
                                   )
                                   Text(
                                       text = note.longitude
                                   )
                               }
                           }
                       }
                   }
               }
           }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                onClick = {
                    scope.launch{
                        viewModel.onEvent(NotesEvent.deleteNoteAt, Note("Title${count.value}", "Content is like this for now. ", "Library", "12", "12"))
                    }
                }) {
                Text(text = "Delete")
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                onClick = {
                    scope.launch{
                        navController.navigate(Screen.AddNoteScreen.route)
                        println("debug: ? ")
                    }
                }) {
                Text(text = "Write note now")
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                onClick = {
                    scope.launch{
                        navController.navigate(Screen.MapScreen.route)
                    }
                }) {
                Text(text = "Jump to Map Page")
            }
        }
    }

    }
