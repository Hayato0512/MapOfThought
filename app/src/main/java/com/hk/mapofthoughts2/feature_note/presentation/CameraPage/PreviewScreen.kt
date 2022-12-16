package com.hk.mapofthoughts2.feature_note.presentation.CameraPage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage.AddNoteViewModel
import java.io.File
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hk.mapofthoughts2.R

//import coil.compose.rememberImagePainter
@Composable
fun PreviewScreen(
    viewModel: CameraViewModel = hiltViewModel(),
) {
    val pathState :String= viewModel.pathState.value
    val savedStateHandle: SavedStateHandle = SavedStateHandle()
    val path : String? = savedStateHandle.get<String>("path")
    if(path !=null){
       println("debug: hey the path is there ${path}")
    }else{
        println("debug: the path does not exist. Please check again. ")
    }
//    savedStateHandle.get<String>("path")?.let {
//        Log.d("PreviewScreen.kt", "hey, just got the path. that is ${it}")
//    }
    val imgFile = File(pathState)
    var imgBitmap: Bitmap? = null
    if (imgFile.exists()) {
        // on below line we are creating an image bitmap variable
        // and adding a bitmap to it from image file.
        imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
    }
//    Image(
//        // on the below line we are specifying the drawable image for our image.
//        // painter = painterResource(id = courseList[it].languageImg),
////        painter = rembember(id = R.drawable.ic_launcher_background),
////        data = imgBitmap
//        // on the below line we are specifying
//        // content description for our image
//        contentDescription = "Image",
//
//        // on the below line we are setting the height
//        // and width for our image.
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(200.dp)
//            .padding(10.dp)
//    )
    Text(
        text="what up??"
    )
    Text(
        text="the path I got from viewModel is ${pathState}??"
    )
}