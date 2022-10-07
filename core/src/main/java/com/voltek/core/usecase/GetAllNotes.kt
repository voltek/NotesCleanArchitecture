package com.voltek.core.usecase

import com.voltek.core.repository.NoteRepository

class GetAllNotes (private val noteRepository: NoteRepository) {

    suspend operator fun invoke() = noteRepository.getAllNotes()
}