package com.example.dima.imagesorter.di.module

import android.app.Application
import android.content.Context
import com.example.dima.imagesorter.di.PreferenceInfo
import com.example.dima.imagesorter.providers.AppPreferenceHelper
import com.example.dima.imagesorter.providers.ImagePathfinder
import com.example.dima.imagesorter.providers.PreferenceHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import io.reactivex.disposables.CompositeDisposable

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    internal  fun provideImagePathfinder(context: Context): ImagePathfinder = ImagePathfinder(context)

    @Provides
    @PreferenceInfo
    internal fun providePrefFileName(): String = "IMAGE SORTER PREF FILE"

    @Provides
    @Singleton
    internal fun providePrefHelper(appPreferenceHelper: AppPreferenceHelper): PreferenceHelper = appPreferenceHelper

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
}