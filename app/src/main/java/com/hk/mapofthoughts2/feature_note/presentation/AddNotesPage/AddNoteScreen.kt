package com.hk.mapofthoughts2.feature_note.presentation.components

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.hk.mapofthoughts2.domain.model.Location2
import com.hk.mapofthoughts2.domain.model.Note
import com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage.AddNoteViewModel
import com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage.ApiInterface
import com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage.WeatherData
import com.hk.mapofthoughts2.feature_note.presentation.Camera
import com.hk.mapofthoughts2.feature_note.presentation.MainActivity
import com.hk.mapofthoughts2.feature_note.presentation.NotesPage.NoteViewModel
import com.hk.mapofthoughts2.feature_note.presentation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun AddNoteScreen(
    navController:NavController,
    fetchLocation:()-> Location2,
    callRequestPermission:()-> Unit,
    location: Location2,
    viewModel: AddNoteViewModel = hiltViewModel(),
){
    val titleState = viewModel.titleState.value
    val contentState = viewModel.contentState.value
    val scope = rememberCoroutineScope()
//        getWeatherData();

//        val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            Log.d("ExampleScreen","PERMISSION GRANTED")
            Log.d("AddNoteScreen","User permitted so let's do something here. open the camera")

        } else {
            // Permission Denied: Do something
            Log.d("ExampleScreen","PERMISSION DENIED")
        }
    }
    val context = LocalContext.current
    Box{
        Column{


            TextTitleField(
                navController = navController,
                currentText = viewModel.titleState.value,
                onValueChange = {newText->
                   viewModel.titleState.value = newText
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(Color.DarkGray)
            )
            TextTitleField(
                navController = navController,
                currentText = contentState,
                onValueChange = {newText->
                    viewModel.contentState.value = newText

                },
                modifier = Modifier
                    .padding(10.dp)
                    .background(Color.LightGray)
            )
            Text(
                text=viewModel.currentImageName.value
            )
            Text(
                text="he???"
            )
            Button(
               onClick={
                   println("debug: AddNoteScreen. ${viewModel.currentImageName.value}")
                   //ok, the problem is , when coming back from Camera, Cannot percist the imageName.
                   //cuz popStackBack.
                   //I need to find a way to make the viewModel still live to keep data.
                   //This is a little tricky for sure. worst case, I would use LocalStorage to fetch the imageNmae. and keep useing popStackBack to keep the content.
                   //Same thing can be done for Audio system too.
                   //Ok I think I did omre than enough today. let's rest.
               }
            ){
                Text(
                    text="check the imageName in viewModel???"
                )
            }
            Button(
                onClick = {
                    // Check permission
                    when (PackageManager.PERMISSION_GRANTED) {
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) -> {
                            // Some works that require permission
                            Log.d("ExampleScreen","Code requires permission")
                        }
                        else -> {
                            // Asking for permission
                            launcher.launch(Manifest.permission.CAMERA)
                        }
                    }
                }
            ) {
                Text(text = "Check and Request Permission")
            }
            Button(
               onClick={
                       navController.navigate("camera_screen")
               },
//            modifier = Modifier.height(30.dp).width(40.dp)
            ){
               Text(text="Camera")
            }
            Button(
                onClick={
                    navController.navigate("audio_screen")
                },
//            modifier = Modifier.height(30.dp).width(40.dp)
            ){
                Text(text="Audio")
            }
            Button(
                onClick = {
                    fetchLocation().also {
                        var locationReturned: Location2 = Location2(it.lat, it.long)
                        println("debug: in ADDNOTESCREEN, locationReturned by fetchLocatin() is lat${locationReturned.lat}, long${locationReturned.long}")
                    }
                        println("debug: now in onClick to insert the note. it.lat is ${location.lat}, it.long is ${location.long}")
                        val noteToInsert = Note(titleState,contentState,"myRoom", location.lat.toString(), location.long.toString(),"", "")
                        scope.launch{
                            viewModel.addNote(noteToInsert)
                        }
                        navController.navigate(Screen.NotesScreen.route)
//                    }
                }

            ) {
                Text(text="submit")

            }
            Button(
                onClick = {
                    var locationReturned:Location2 = Location2(-1, -1)
                     fetchLocation().also {
                       locationReturned.lat = it.lat
                         locationReturned.long = it.long
                         println("debug: OK! in AddNoteScreen, we got locationReturned ${locationReturned.lat}, ${locationReturned.long}")
                    }
                    println("debug: OK! in AddNoteScreen, we got ${location.lat}, ${location.long}")
                    navController.navigate(Screen.NotesScreen.route)
                    }
            ) {
                Text(text="getLocation")
            }
//            Camera()
        }
    }

}

fun getWeatherData() {
    val BASE_URL = "https://open-weather13.p.rapidapi.com/city/"
    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(ApiInterface::class.java)

    val retrofitData = retrofitBuilder.getData()

    retrofitData.enqueue(object : Callback<List<WeatherData>?> {
        override fun onResponse(
            call: Call<List<WeatherData>?>,
            response: Response<List<WeatherData>?>
        ) {
            val responseBody = response.body()!!

            for(myData in responseBody){
                println("debug: hello, myData.id is here .${myData.id}")
            }
        }

        override fun onFailure(call: Call<List<WeatherData>?>, t: Throwable) {
            println("debug: onFailure22 ${t.message}")
        }
    })


}
