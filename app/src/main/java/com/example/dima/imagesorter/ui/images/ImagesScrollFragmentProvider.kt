package com.example.dima.imagesorter.ui.images

import com.example.dima.imagesorter.ui.images.view.ImagesScrollFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ImagesScrollFragmentProvider {
    //this annotation generates AndroidInjector for returning type
    @ContributesAndroidInjector(modules = [ImagesScrollFragmentModule::class])
    internal abstract fun provideImagesScrollFragmentFactory(): ImagesScrollFragment
}