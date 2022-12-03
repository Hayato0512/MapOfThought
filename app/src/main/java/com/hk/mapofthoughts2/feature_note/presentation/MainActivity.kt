package com.hk.mapofthoughts2.feature_note.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hk.mapofthoughts2.R
import com.hk.mapofthoughts2.feature_note.presentation.MapPage.MapScreen
import com.hk.mapofthoughts2.feature_note.presentation.components.AddNoteScreen
import com.hk.mapofthoughts2.feature_note.presentation.components.NotesScreen
import com.hk.mapofthoughts2.theme.MapOfThoughtsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        setContent {
        
            MapOfThoughtsAppTheme {
               
                Surface {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination =  Screen.NotesScreen.route) {

                    composable(route = Screen.NotesScreen.route){
                        NotesScreen(navController = navController)
                    }

                    composable(route = Screen.AddNoteScreen.route){
                        AddNoteScreen(navController = navController)
                    }
                    composable(route = Screen.MapScreen.route){
                        MapScreen(navController = navController)
                    }
                }
                }
                
            }
        }
    }
}