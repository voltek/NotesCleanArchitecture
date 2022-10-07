package com.voltek.core.usecase

import com.voltek.core.repository.NoteRepository

class GetNote(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(id: Long) = noteRepository.getNote(id)
}