package com.voltek.cleanarchitectureproject.framework.di

import android.app.Application
import com.voltek.cleanarchitectureproject.framework.RoomNoteDataSource
import com.voltek.cleanarchitectureproject.framework.database.mapper.NoteMapper
import com.voltek.core.repository.NoteRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(app: Application) = NoteRepository(RoomNoteDataSource(app, NoteMapper))
}