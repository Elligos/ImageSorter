package com.example.dima.imagesorter.ui.groups

import com.example.dima.imagesorter.ui.groups.presenter.GroupPickerMVPPresenter
import com.example.dima.imagesorter.ui.groups.presenter.GroupPickerPresenter
import com.example.dima.imagesorter.ui.groups.view.GroupPickerMVPView

import dagger.Module
import dagger.Provides

@Module
class GroupPickerFragmentModule {
    @Provides
    internal  fun provideGroupPickerPresenter(presenter : GroupPickerPresenter<GroupPickerMVPView>)
            : GroupPickerMVPPresenter<GroupPickerMVPView> = presenter
}