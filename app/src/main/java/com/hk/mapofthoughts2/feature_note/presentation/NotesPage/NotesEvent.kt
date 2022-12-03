package com.hk.mapofthoughts2.feature_note.presentation.NotesPage

sealed class NotesEvent{
    object addNote: NotesEvent()
    object deleteNote: NotesEvent()
    object deleteNoteAt: NotesEvent()
    object getAllNotes: NotesEvent()
}
