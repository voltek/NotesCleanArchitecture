package com.voltek.cleanarchitectureproject.framework.database

import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Long): NoteEntity?

    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): List<NoteEntity>

    @Delete
    suspend fun deleteNote(note: NoteEntity)
}