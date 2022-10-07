package com.voltek.core.repository

import com.voltek.core.data.Note

interface NoteDataSource {

    suspend fun add(note: Note)

    suspend fun getNote(id: Long): Note

    suspend fun getAllNotes(): List<Note>

    suspend fun remove(note: Note)
}