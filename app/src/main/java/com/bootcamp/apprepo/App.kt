package com.bootcamp.apprepo

import android.app.Application
import com.bootcamp.apprepo.data.di.DataModule
import com.bootcamp.apprepo.domain.di.DomainModule
import com.bootcamp.apprepo.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        DataModule.load()
        DomainModule.load()
        PresentationModule.load()
    }
}