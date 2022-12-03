package com.hk.mapofthoughts2.feature_note.presentation.components

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hk.mapofthoughts2.domain.model.Note
import com.hk.mapofthoughts2.feature_note.presentation.NoteViewModel
import com.hk.mapofthoughts2.feature_note.presentation.NotesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
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
                       Text(
                           text = note.title
                       )
                   }
               }
           }

            //why is this button not showing up?
            //So it was there. But was out of screen?? what is weird
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    onClick = {
                        println("debug: button Clicked")
                    }) {
                    Text(text = "buttonUnkoUnko")
                }
            }
        }

        Row{

            Button(onClick = {
scope.launch {
    viewModel.onEvent(NotesEvent.addNote, Note("Title${count.value}", "Content is like this for now. ", "Library", "12", "12"))
    println("debug: hey in NotesScreen, we jsut called viewModel to addNote")
}
            }, modifier = Modifier.padding(16.dp)) {

                Text(
                    text = "Write the button text here"
                )
            }

            Button(onClick={
                count.value ++;
            }){

Text(
    text = "hey you touched me ${count.value} times"
)
            }
        }
    }

@Composable
fun ButtonExample() {
    Button(onClick = { /* Do something! */ }, colors = ButtonDefaults.textButtonColors(
        backgroundColor = Color.Red
    )) {
        Text("ButtonExample")
    }
}