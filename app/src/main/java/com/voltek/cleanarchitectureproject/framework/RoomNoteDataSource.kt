package com.voltek.cleanarchitectureproject.framework

import android.content.Context
import com.voltek.cleanarchitectureproject.framework.database.DatabaseService
import com.voltek.cleanarchitectureproject.framework.database.NoteEntity
import com.voltek.cleanarchitectureproject.framework.database.mapper.NoteMapper
import com.voltek.core.data.Note
import com.voltek.core.repository.NoteDataSource
import com.voltek.core.utils.Constants.EMPTY_STRING

class RoomNoteDataSource(context: Context, private val noteMapper: NoteMapper) : NoteDataSource {

    private val noteDao = DatabaseService.getInstance(context).noteDao()

    override suspend fun add(note: Note) {
        noteDao.insertNote(noteMapper.fromNote(note))
    }

    override suspend fun getNote(id: Long): Note {
        val noteEntity = noteDao.getNoteById(id) ?: getEmptyNoteEntity()
        return noteMapper.toNote(noteEntity)
    }

    override suspend fun getAllNotes(): List<Note> {
        return noteDao.getAllNotes().map { noteMapper.toNote(it) }
    }

    override suspend fun remove(note: Note) {
        return noteDao.deleteNote(noteMapper.fromNote(note))
    }

    private fun getEmptyNoteEntity(): NoteEntity {
        return NoteEntity(
            title = EMPTY_STRING,
            content = EMPTY_STRING,
            creationTime = 0,
            updateTime = 0
        )
    }
}