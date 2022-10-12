package com.voltek.cleanarchitectureproject.framework.di

import com.voltek.cleanarchitectureproject.framework.viewmodels.ListViewModel
import com.voltek.cleanarchitectureproject.framework.viewmodels.NoteViewModel
import dagger.Component

@Component(modules = [ApplicationModule::class, RepositoryModule::class, UseCasesModule::class])
interface ViewModelComponent {
    fun inject(noteListViewModel: ListViewModel)
    fun inject(noteViewModel: NoteViewModel)
}