package com.voltek.core.repository

import com.voltek.core.data.Note

class NoteRepository(private val dataSource: NoteDataSource) {

    suspend fun addNote(note: Note) = dataSource.add(note)

    suspend fun getNote(id: Long) = dataSource.getNote(id)

    suspend fun getAllNotes() = dataSource.getAllNotes()

    suspend fun remove(note: Note) = dataSource.remove(note)
}