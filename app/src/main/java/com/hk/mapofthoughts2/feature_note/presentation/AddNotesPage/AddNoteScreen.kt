package com.hk.mapofthoughts2.feature_note.presentation.components

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.hk.mapofthoughts2.domain.model.Location2
import com.hk.mapofthoughts2.domain.model.Note
import com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage.AddNoteViewModel
import com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage.ApiInterface
import com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage.WeatherData
import com.hk.mapofthoughts2.feature_note.presentation.MainActivity
import com.hk.mapofthoughts2.feature_note.presentation.NotesPage.NoteViewModel
import com.hk.mapofthoughts2.feature_note.presentation.Screen
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("MissingPermission")
@Composable
fun AddNoteScreen(
    navController:NavController,
    fetchLocation:()-> Location2,
    location: Location2,
    viewModel: AddNoteViewModel = hiltViewModel(),
){
    val titleState = viewModel.titleState.value
    val contentState = viewModel.contentState.value
    val scope = rememberCoroutineScope()
//        getWeatherData();

    Box{
        Column{


            TextTitleField(
                navController = navController,
                currentText = titleState,
                onValueChange = {newText->
                   viewModel.titleState.value = newText
                },
                modifier = Modifier.padding(16.dp).fillMaxWidth().background(Color.DarkGray)
            )
            TextTitleField(
                navController = navController,
                currentText = contentState,
                onValueChange = {newText->
                    viewModel.contentState.value = newText

                },
                modifier = Modifier.padding(10.dp).background(Color.LightGray)
            )
            Button(
                onClick = {
                    fetchLocation().also {
                        var locationReturned: Location2 = Location2(it.lat, it.long)
                        println("debug: in ADDNOTESCREEN, locationReturned by fetchLocatin() is lat${locationReturned.lat}, long${locationReturned.long}")
                    }
                        println("debug: now in onClick to insert the note. it.lat is ${location.lat}, it.long is ${location.long}")
                        val noteToInsert = Note(titleState,contentState,"myRoom", location.lat.toString(), location.long.toString())
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
