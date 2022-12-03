package com.hk.mapofthoughts2.feature_note.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleButton(
    modifier: Modifier
){

    Box(modifier = Modifier){
       Button(onClick = { println("debug: buttonClicked") }, shape = CutCornerShape(10)){
          Text(text = "button")
       }
    }
}