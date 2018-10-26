package com.example.dima.imagesorter.di.builder

import com.example.dima.imagesorter.ui.images.ImagesScrollFragmentModule
import com.example.dima.imagesorter.ui.images.ImagesScrollFragmentProvider
import com.example.dima.imagesorter.ui.main.MainActivityModule
import com.example.dima.imagesorter.ui.main.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(MainActivityModule::class),(ImagesScrollFragmentProvider::class)])
    abstract fun bindMainActivity(): MainActivity

}