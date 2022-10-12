package com.voltek.cleanarchitectureproject.framework.di

import com.voltek.cleanarchitectureproject.framework.NoteUseCases
import com.voltek.core.repository.NoteRepository
import com.voltek.core.usecase.*
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun provideUseCases(noteRepository: NoteRepository) = NoteUseCases(
        AddNote(noteRepository),
        GetAllNotes(noteRepository),
        GetNote(noteRepository),
        RemoveNote(noteRepository),
        GetWordCount(),
    )
}