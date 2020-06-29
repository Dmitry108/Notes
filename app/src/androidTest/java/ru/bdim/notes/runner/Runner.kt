package ru.bdim.notes.runner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import org.koin.android.ext.android.startKoin

class Runner : AndroidJUnitRunner(){
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, AppTest::class.java.name, context)
    }
}
class AppTest : Application(){
    override fun onCreate() {
        startKoin(this, emptyList())
        super.onCreate()
    }
}