package com.voltek.cleanarchitectureproject.framework.database.mapper

import com.voltek.cleanarchitectureproject.framework.database.NoteEntity
import com.voltek.core.data.Note

object NoteMapper {

    fun fromNote(note: Note): NoteEntity = NoteEntity(
        id = note.id,
        title = note.title,
        content = note.content,
        creationTime = note.creationTime,
        updateTime = note.updateTime,
    )

    fun toNote(entity: NoteEntity): Note = Note(
        id = entity.id,
        title = entity.title,
        content = entity.content,
        creationTime = entity.creationTime,
        updateTime = entity.updateTime,
    )
}