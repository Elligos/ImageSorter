package com.example.dima.imagesorter.di.builder

import com.example.dima.imagesorter.items.browser.ImageItemsBrowser
import com.example.dima.imagesorter.ui.groups.GroupPickerFragmentProvider
import com.example.dima.imagesorter.ui.images.ItemsDisplayFragmentProvider
import com.example.dima.imagesorter.ui.main.MainActivityModule
import com.example.dima.imagesorter.ui.main.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(MainActivityModule::class),(ItemsDisplayFragmentProvider::class), (GroupPickerFragmentProvider::class)])
    abstract fun bindMainActivity(): MainActivity



}