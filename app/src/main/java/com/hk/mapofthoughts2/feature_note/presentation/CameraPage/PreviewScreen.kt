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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.hk.mapofthoughts2.R
import com.hk.mapofthoughts2.feature_note.presentation.MoreInfoPage.MoreInfoDetailViewModel
import com.hk.mapofthoughts2.feature_note.presentation.MoreInfoPage.MoreInfoViewModel
import com.hk.mapofthoughts2.feature_note.presentation.Screen

//import coil.compose.rememberImagePainter
@Composable
fun PreviewScreen(
    navController: NavController,
    moreInfoDetailViewModel: MoreInfoDetailViewModel,
    viewModel: CameraViewModel = hiltViewModel(),
    addNoteViewModel: AddNoteViewModel = hiltViewModel(),
) {
    val pathState :String= moreInfoDetailViewModel.imageNameState.value
    val savedStateHandle: SavedStateHandle = SavedStateHandle()
    val path : String? = savedStateHandle.get<String>("path")
    val imageName = pathState?.split("/")
    println("debug: HEYHEYHEY, imageNaem is ${imageName}")
    val fakePath: String? = "/storage/emulated/0/Android/media/com.hk.mapofthoughts2/MapOfThoughts2/202212350-124625.jpg"
    if(pathState !=null){
       println("debug: hey the path is there ${path}")
    }else{
        println("debug: the path does not exist. Please check again. ")
    }
    val imgFile = File(pathState)
//    val imgFile = File(pathState)
    var imgBitmap: Bitmap? = null
    if (imgFile.exists()) {
        // on below line we are creating an image bitmap variable
        // and adding a bitmap to it from image file.
        imgBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
    }else{
       println("debug: imgFile does not exist in the specified directory. meaning, saving the image has not been done.")
    }
    Column{
        Image(
            // on the below line we are specifying the drawable image for our image.
            painter = rememberImagePainter(data = imgBitmap),
            // on the below line we are specifying
            // content description for our image
            contentDescription = "Image",

            // on the below line we are setting the height
            // and width for our image.
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(10.dp)
        )
//        Text(
//            text="the path I got from viewModel is ${pathState}??"
//        )
//        Button(
//           onClick={
//              //set the path name to addNoteVIewModel.currentImageName
//            addNoteViewModel.currentImageName.value= pathState
//               println("debug: YO!YO! PreviewScreen. ${addNoteViewModel.currentImageName.value}")
//               navController.navigate(Screen.AddNoteScreen.route)
//           }
//        ){
//           Text(
//               text="choose this photo"
//           )
//        }
    }
}