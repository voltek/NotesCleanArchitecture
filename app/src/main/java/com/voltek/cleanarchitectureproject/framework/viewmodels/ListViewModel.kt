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

class ListViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var useCases: NoteUseCases

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    val notes = MutableLiveData<List<Note>>()

    init {
        DaggerViewModelComponent.builder()
            .applicationModule(ApplicationModule(application))
            .build()
            .inject(this)
    }

    fun getAllNotes() {
        coroutineScope.launch {
            val noteList = useCases.getAllNotes()

            noteList.forEach { it.wordCount = useCases.getWordCount(it) }
            notes.postValue(noteList)
        }
    }
}