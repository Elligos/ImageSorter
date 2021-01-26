package com.example.dima.imagesorter.ui.images

import com.example.dima.imagesorter.ui.images.view.ItemsDisplayFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ItemsDisplayFragmentProvider {
    //this annotation generates AndroidInjector for returning type
    @ContributesAndroidInjector(modules = [ItemsDisplayFragmentModule::class])
    internal abstract fun provideItemsDisplayFragmentFactory(): ItemsDisplayFragment
}