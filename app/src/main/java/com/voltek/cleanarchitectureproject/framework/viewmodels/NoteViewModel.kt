package com.voltek.cleanarchitectureproject.framework.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.voltek.cleanarchitectureproject.framework.NoteUseCases
import com.voltek.cleanarchitectureproject.framework.RoomNoteDataSource
import com.voltek.cleanarchitectureproject.framework.database.mapper.NoteMapper
import com.voltek.core.data.Note
import com.voltek.core.repository.NoteRepository
import com.voltek.core.usecase.AddNote
import com.voltek.core.usecase.GetAllNotes
import com.voltek.core.usecase.GetNote
import com.voltek.core.usecase.RemoveNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private val noteMapper = NoteMapper
    private val noteRepository = NoteRepository(RoomNoteDataSource(application, noteMapper))

    private val useCases: NoteUseCases = NoteUseCases(
        AddNote(noteRepository),
        GetAllNotes(noteRepository),
        GetNote(noteRepository),
        RemoveNote(noteRepository)
    )

    val saved = MutableLiveData<Boolean>()

    fun saveNote(note: Note) {
        coroutineScope.launch {
            useCases.addNote.invoke(note)
            saved.postValue(true)
        }
    }
}