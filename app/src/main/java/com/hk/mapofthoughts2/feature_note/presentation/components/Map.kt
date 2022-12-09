package com.hk.mapofthoughts2.feature_note.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
//import com.google.android.gms.maps.model.CameraPosition
//import com.google.android.gms.maps.model.LatLng
//import com.google.maps.android.compose.GoogleMap
//import com.google.maps.android.compose.Marker
//import com.google.maps.android.compose.MarkerState
//import com.google.maps.android.compose.rememberCameraPositionState
import com.hk.mapofthoughts2.feature_note.presentation.NotesPage.NoteViewModel

@Composable
fun MapComponent(
   navController:NavController,
  viewModel:NoteViewModel = hiltViewModel(),
){

    val state = viewModel.state.value // ok this is connected to viewModel. Very very very fucking nice.
    val vancouver = LatLng(49.2827, -123.1207)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(vancouver, 10f)
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            state.notes.forEach{

            Marker(
                state = MarkerState(position = LatLng(it.latitude.toDouble(),it.longitude.toDouble())),
                title = it.title,
                snippet = it.content
            )

            }

        }

    }

}