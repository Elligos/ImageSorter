package com.example.dima.imagesorter.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides


@Module
class ActivityModule(val activity: AppCompatActivity) {


    @Provides
//    @ActivityContext
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideActivity(): AppCompatActivity {
        return activity
    }


}