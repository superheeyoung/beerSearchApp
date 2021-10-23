package com.example.beersearchapp

import androidx.multidex.MultiDexApplication
import com.example.beersearchapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

class BeerApplication : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BeerApplication)
            modules(appModule)
            EmptyLogger()
        }
    }

}