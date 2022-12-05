package com.hk.mapofthoughts2.feature_note.presentation

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.hk.mapofthoughts2.R
import com.hk.mapofthoughts2.domain.model.Location2
import com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage.AddNoteViewModel
import com.hk.mapofthoughts2.feature_note.presentation.MapPage.MapScreen
import com.hk.mapofthoughts2.feature_note.presentation.components.AddNoteScreen
import com.hk.mapofthoughts2.feature_note.presentation.components.NotesScreen
import com.hk.mapofthoughts2.theme.MapOfThoughtsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity(
) : AppCompatActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var locationState  = mutableStateOf(Location2(-1, -1))
         fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this)
        var locationToPass = Location2(-1,-1)

//        fetchLocation()
        setContent {
        
            MapOfThoughtsAppTheme {
               
                Surface {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination =  Screen.NotesScreen.route) {

                    composable(route = Screen.NotesScreen.route){
                        NotesScreen(navController = navController)
                    }

                    composable(route = Screen.AddNoteScreen.route){
                        fetchLocation()
                        AddNoteScreen(navController = navController, {fetchLocation()},fetchLocation())
                    }
                    composable(route = Screen.MapScreen.route){
                        MapScreen(navController = navController )
                    }
                }
                }
                
            }
        }

    //or maybe just make an json object and then return it
}
private fun fetchLocation(): Location2 {
    val task = fusedLocationProviderClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY,object : CancellationToken() {
        override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

        override fun isCancellationRequested() = false
    })
    var returnLocation: Location2 =Location2(-1,-1)
    if(
        ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED &&ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
    ){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)

    }
    task.addOnSuccessListener {
        if(it != null){
            Toast.makeText(applicationContext,"${it.latitude}, ${it.longitude}", Toast.LENGTH_SHORT).show()
            println("debug: ok now, we got the lat long ${it.latitude}, ${it.longitude}")
            returnLocation?.lat = it.latitude
            returnLocation?.long = it.longitude
//            locationState.value.lat = it.latitude
//            locationState.value.long = it.longitude
        }
        else{
            println("debug: returnedLocation is null")
        }
    }
    return returnLocation
}
}
