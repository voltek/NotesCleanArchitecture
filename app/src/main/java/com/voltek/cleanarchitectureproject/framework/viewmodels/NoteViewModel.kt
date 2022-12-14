package com.voltek.cleanarchitectureproject.framework.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.voltek.cleanarchitectureproject.framework.NoteUseCases
import com.voltek.cleanarchitectureproject.framework.di.ApplicationModule
import com.voltek.cleanarchitectureproject.framework.di.DaggerViewModelComponent
import com.voltek.core.data.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var useCases: NoteUseCases

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    val saved = MutableLiveData<Boolean>()
    val currentNote = MutableLiveData<Note?>()

    init {
        DaggerViewModelComponent.builder()
            .applicationModule(ApplicationModule(application))
            .build()
            .inject(this)
    }

    fun saveNote(note: Note) {
        coroutineScope.launch {
            useCases.addNote.invoke(note)
            saved.postValue(true)
        }
    }

    fun getNoteById(id: Long) {
        coroutineScope.launch {
            currentNote.postValue(useCases.getNote.invoke(id))
        }
    }

    fun removeNote(note: Note) {
        coroutineScope.launch {
            useCases.removeNote.invoke(note)
            saved.postValue(true)
        }
    }
}