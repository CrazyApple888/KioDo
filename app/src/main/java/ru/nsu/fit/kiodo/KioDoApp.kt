package ru.nsu.fit.kiodo

import android.app.Application
import ru.nsu.fit.kiodo.di.AppComponent
import ru.nsu.fit.kiodo.di.DaggerAppComponent
import ru.nsu.fit.kiodo.di.DataModule

class KioDoApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(applicationContext)
            .dataModule(DataModule(applicationContext))
            .build()
    }
}