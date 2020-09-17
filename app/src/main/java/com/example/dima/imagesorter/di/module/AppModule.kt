package com.example.dima.imagesorter.di.module

import android.app.Application
import android.content.Context
import com.example.dima.imagesorter.providers.ImagePathfinder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    internal  fun provideImagePathfinder(context: Context): ImagePathfinder = ImagePathfinder(context)
}