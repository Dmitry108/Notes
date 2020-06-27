package ru.bdim.notes.di

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import ru.bdim.notes.model.Repository
import ru.bdim.notes.model.source.FirestoreProvider
import ru.bdim.notes.viewmodel.MainViewModel
import ru.bdim.notes.viewmodel.NoteViewModel
import ru.bdim.notes.viewmodel.SplashViewModel

val appModule = module {
    single { Repository(FirestoreProvider()) }
}
val splashModule = module {
    viewModel { SplashViewModel(get()) }
}
val mainModule = module {
    viewModel { MainViewModel(get()) }
}
val noteModule = module {
    viewModel { NoteViewModel(get()) }
}