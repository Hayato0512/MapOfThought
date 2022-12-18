package com.hk.mapofthoughts2.feature_note.presentation.AudioPage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.hk.mapofthoughts2.feature_note.presentation.MainActivity

@Composable
fun AudioScreen(
   navController: NavController,
   activity:MainActivity
) {
    Column{

        Text(
            text = "Audio Screen Successful"
        )
        Button(
            onClick = {
                activity.requestAudioRecording()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "StartRecording"
            )
        }
            Button(
                onClick = {
                    activity.requestStopRecording()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "StopRecording"
                )
            }
    }
    }
