package com.hk.mapofthoughts2.feature_note.presentation

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hk.mapofthoughts2.R
import com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage.AddNoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

@Composable
fun Camera(
    navController: NavController,
    outputDirectory : File,
    onMediaCaptured: (Uri?) -> Unit,
    addNoteViewModel: AddNoteViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val cameraProviderFuture = remember{
       ProcessCameraProvider.getInstance(context)
    }

    var imageCapture: ImageCapture?  =  remember { mutableStateOf(null) }.value


    //how do we access previewView inside of composable
    val previewView = remember {
        PreviewView(context).apply{
            id = R.id.preview_view
        }
    }

    val cameraExecutor = remember{
        Executors.newSingleThreadExecutor()
    }
    Column{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .weight(1f)

        ){

            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            ){
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = androidx.camera.core.Preview.Builder()
                        .build()
                        .also{
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    val faceAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also{
                            it.setAnalyzer(cameraExecutor,FaceAnalyzer())
                        }
                    imageCapture = ImageCapture.Builder()
                        .setTargetRotation(previewView.display.rotation)
                        .build()
                    try{
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(lifecycleOwner,cameraSelector,preview,imageCapture, faceAnalysis)
                    }catch(e:Exception){
                        Log.e("Exc", "CameraX ${e.localizedMessage}")
                    }
                }, ContextCompat.getMainExecutor(context))

            }


            Button(
                onClick = {
                    val imgCapture = imageCapture ?: return@Button
                    val photoFile = File(
                        outputDirectory,
                        SimpleDateFormat("yyyyMMDD-HHmmss", Locale.US)
                            .format(System.currentTimeMillis()) + ".jpg"
                    )
                    Log.d("Camera.kt", "outputDirectory ${outputDirectory.name}")
                    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
                    imgCapture.takePicture(
                        outputOptions,
                        cameraExecutor,
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                Log.d("Camera.kt", "onImageSaved, onMediaCaptured Lets go")
                                Log.d("Camera.kt", "onImageSaved, photoFile is${photoFile}")
                                /// photoFIle = "/storage/emulated/0/Android/media/com.hk.mapofthoughts2/MapOfThoughts2/202212350-124625.jpg"

                                Log.d("Camera.kt", "onImageSaved, outputFileResults is${outputFileResults}")
                                onMediaCaptured(Uri.fromFile(photoFile))

                                //open previewPage, with parameter photoFIle.
                                //get access to MoreInfoViewModel and then do it
                                addNoteViewModel.currentImageName.value = photoFile.toString()
                                CoroutineScope(Dispatchers.Main)
                                .launch{
//                                    navController.navigate(Screen.AddNoteScreen.route)
                                    addNoteViewModel.isCameraScreen.value = false
                                }
                            }

                            override fun onError(exception: ImageCaptureException) {
                                Toast.makeText(context, "${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
                                Log.d("Camera.kt", "${exception.localizedMessage}")
                            }
                        }
                    )
                },
                modifier = Modifier
                    .size(70.dp)
                    .background(Color.Yellow, CircleShape)
                    .shadow(4.dp, CircleShape)
                    .clip(CircleShape)
                    .border(5.dp, Color.LightGray, CircleShape),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow),
            ) {

            }
        }

        val imgFile = File("/storage/emulated/0/Download/GeekforGeeksphoto.png")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .weight(1f)
        ){
           Text(text = "Picture comes here.")
        }
    }

}
private class FaceAnalyzer():ImageAnalysis.Analyzer{
    override fun analyze(imageProxy: ImageProxy) {
        val image = imageProxy.image

        image?.close()
    }

}