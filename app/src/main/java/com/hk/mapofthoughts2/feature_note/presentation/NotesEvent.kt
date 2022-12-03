package com.hk.mapofthoughts2.feature_note.presentation

import com.hk.mapofthoughts2.domain.model.Note

sealed class NotesEvent{
    object addNote:NotesEvent()
    object deleteNote:NotesEvent()
    object getAllNotes:NotesEvent()
}
