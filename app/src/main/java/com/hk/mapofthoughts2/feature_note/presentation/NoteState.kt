package com.hk.mapofthoughts2.feature_note.presentation

import com.hk.mapofthoughts2.domain.model.Note


data class NoteState(
    val notes: List<Note> = emptyList(),
)
