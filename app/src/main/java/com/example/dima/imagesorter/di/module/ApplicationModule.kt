package com.example.dima.imagesorter.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(val application : Application) {

    @Provides
//    @ApplicationContext
    fun provideContext() : Context {
        return application
    }

    @Provides
    fun  provideApplication() : Application{
        return application
    }
}