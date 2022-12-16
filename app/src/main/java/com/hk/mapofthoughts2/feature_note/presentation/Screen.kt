package com.hk.mapofthoughts2.feature_note.presentation

sealed class Screen(val  route: String){
    object NotesScreen: Screen("notes_screen")
    object AddNoteScreen: Screen("add_note_screen")
    object MoreInfoScreen: Screen("more_info_screen")
    object MapScreen: Screen("map_screen")
    object CameraScreen: Screen("camera_screen")
    object PreviewScreen: Screen("preview_screen")
}
