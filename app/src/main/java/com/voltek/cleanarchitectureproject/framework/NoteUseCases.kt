package com.voltek.cleanarchitectureproject.framework

import com.voltek.core.usecase.AddNote
import com.voltek.core.usecase.GetAllNotes
import com.voltek.core.usecase.GetNote
import com.voltek.core.usecase.RemoveNote

data class NoteUseCases(
    val addNote: AddNote,
    val getAllNotes: GetAllNotes,
    val getNote: GetNote,
    val removeNote: RemoveNote,
)