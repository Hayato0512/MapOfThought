package com.hk.mapofthoughts2.feature_note.presentation.NotesPage

import com.hk.mapofthoughts2.domain.model.Note


data class NoteState(
    val notes: List<Note> = emptyList(),
)
