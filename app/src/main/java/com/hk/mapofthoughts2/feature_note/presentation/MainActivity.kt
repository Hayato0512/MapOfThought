package com.hk.mapofthoughts2.feature_note.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.hk.mapofthoughts2.R
import com.hk.mapofthoughts2.data.NoteDao
import com.hk.mapofthoughts2.domain.model.Location2
import com.hk.mapofthoughts2.domain.repository.NoteRepository
import com.hk.mapofthoughts2.domain.repository.NoteRepositoryImpl
import com.hk.mapofthoughts2.feature_note.presentation.AddNotesPage.AddNoteViewModel
import com.hk.mapofthoughts2.feature_note.presentation.AudioPage.AudioScreen
import com.hk.mapofthoughts2.feature_note.presentation.AudioPage.AudioViewModel
import com.hk.mapofthoughts2.feature_note.presentation.CameraPage.CameraScreen
import com.hk.mapofthoughts2.feature_note.presentation.CameraPage.PreviewScreen
import com.hk.mapofthoughts2.feature_note.presentation.MapPage.MapScreen
import com.hk.mapofthoughts2.feature_note.presentation.MoreInfoPage.MoreInfoScreen
import com.hk.mapofthoughts2.feature_note.presentation.MoreInfoPage.MoreInfoViewModel
import com.hk.mapofthoughts2.feature_note.presentation.components.AddNoteScreen
import com.hk.mapofthoughts2.feature_note.presentation.components.NotesScreen
import com.hk.mapofthoughts2.theme.MapOfThoughtsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val audioViewModel = AudioViewModel()
    private val moreInfoViewModel = MoreInfoViewModel(this)
//    private val addNoteViewModel =AddNoteViewModel(noteRepository)
    val activity = this
    private val requestPermissionLauncherAudio = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted){
            startRecording()
            println("debug: access Granted!! so startRecording() in MAinActivity")
        }
        else{
            Log.d("MainActivity", "permission DINIED")
        }
    }
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if (isGranted){
            Log.i("kilo", "Permission Granted")
        }else{
            Log.i("kilo", "Permission Denied")
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        audioViewModel.folderPath = externalCacheDir?.absolutePath
//            ?: throw IllegalStateException("debug: externalCacheDir is null! LILLOOO!")
//        moreInfoViewModel.folderPath = externalCacheDir?.absolutePath
//            ?: throw IllegalStateException("debug: externalCacheDir is null! LILLOOO!")

        audioViewModel.folderPath = getDirectory().toString()
        println("debug: hey, this is Main. audioViewModel.folderPath is ${audioViewModel.folderPath}")
        moreInfoViewModel.folderPath = getDirectory().toString()
//        requestCameraPermission()
        var locationState  = mutableStateOf(Location2(-1, -1))
         fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this)
        var locationToPass = Location2(-1,-1)

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
                        AddNoteScreen(navController = navController, {fetchLocation()},{callRequestPermission()},fetchLocation(),getDirectory())
                    }
                    composable(route = Screen.MapScreen.route){
                        MapScreen(navController = navController )
                    }
                    composable(route = Screen.AudioScreen.route){
                        AudioScreen(navController = navController ,activity)
                    }
//                    composable(route = Screen.CameraScreen.route){
////                        CameraScreen(navController = navController )
//                    }
                    composable(
                        route = Screen.MoreInfoScreen.route +
                                "?noteId={noteId}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ){
                                type = NavType.IntType
                                defaultValue= -1
                            },
                        )

                    ){
                        MoreInfoScreen(navController = navController , activity)
                    }
                    composable(
                        route = Screen.PreviewScreen.route +
                                "?path={path}",
                        arguments = listOf(
                            navArgument(
                                name = "path"
                            ){
                                type = NavType.StringType
                                defaultValue=""
                            },
                        )

                    ){
                        PreviewScreen(navController)
                    }
                }
                }
                
            }
        }

    //or maybe just make an json object and then return it
}

    private fun getDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
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

    fun requestAudioRecording(){
        //request, if Granted, call startRecording
        requestPermissionLauncherAudio.launch(Manifest.permission.RECORD_AUDIO)
    }
    fun requestStopRecording(){
        stopRecording()
    }
    private fun startRecording(){
       println("debug: in startRecording in mainActivity. viewModel.startRecording")
       audioViewModel.startRecording()
    }
    private fun stopRecording(){
        audioViewModel.stopRecording()
    }

}

fun callRequestPermission(){

    }


//    private fun requestCameraPermission(){
//        when{
//            ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.CAMERA
//            )==PackageManager.PERMISSION_GRANTED->{
//                Log.i("kilo", "Permission previously granted")
//            }
//            ActivityCompat.shouldShowRequestPermissionRationale(
//                this,
//                Manifest.permission.CAMERA
//            )-> Log.i("kilo", "Show camera  permissions dialog")
//            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
//        }
//    }
