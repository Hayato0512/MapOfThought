package com.hk.mapofthoughts2.feature_note.presentation

sealed class Screen(val  route: String){
    object NotesScreen: Screen("notes_screen")
    object MoreInfoScreen: Screen("more_info_screen")
    object MapScreen: Screen("map_screen")
}
