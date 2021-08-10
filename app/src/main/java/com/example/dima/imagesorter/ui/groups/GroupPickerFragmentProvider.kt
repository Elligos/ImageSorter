package com.example.dima.imagesorter.ui.groups

import com.example.dima.imagesorter.ui.groups.view.GroupPickerFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton


@Module
internal abstract class GroupPickerFragmentProvider {
    //this annotation generates AndroidInjector for returning type
    @ContributesAndroidInjector(modules = [GroupPickerFragmentModule::class])
    internal abstract fun provideGroupPickerFragmentFactory(): GroupPickerFragment
}