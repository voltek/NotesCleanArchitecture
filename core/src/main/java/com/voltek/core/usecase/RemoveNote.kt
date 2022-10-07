package com.voltek.core.usecase

import com.voltek.core.data.Note
import com.voltek.core.repository.NoteRepository

class RemoveNote(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(note: Note) = noteRepository.remove(note)
}