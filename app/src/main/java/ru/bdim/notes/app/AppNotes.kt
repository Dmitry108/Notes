package ru.bdim.notes.app

import androidx.multidex.MultiDexApplication
import org.koin.android.ext.android.startKoin
import ru.bdim.notes.di.appModule
import ru.bdim.notes.di.mainModule
import ru.bdim.notes.di.noteModule
import ru.bdim.notes.di.splashModule

class AppNotes : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, splashModule, mainModule, noteModule))
    }
}