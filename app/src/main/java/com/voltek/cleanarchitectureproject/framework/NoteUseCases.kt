package com.voltek.cleanarchitectureproject.framework

import com.voltek.core.usecase.*

data class NoteUseCases(
    val addNote: AddNote,
    val getAllNotes: GetAllNotes,
    val getNote: GetNote,
    val removeNote: RemoveNote,
    val getWordCount: GetWordCount,
)