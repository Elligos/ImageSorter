package com.example.dima.imagesorter.ui.main

import com.example.dima.imagesorter.ui.base.presenter.MVPPresenter
import com.example.dima.imagesorter.ui.base.view.MVPView
import com.example.dima.imagesorter.ui.main.presenter.MainMVPPresenter
import com.example.dima.imagesorter.ui.main.presenter.MainPresenter
import com.example.dima.imagesorter.ui.main.view.MainMVPView
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {


    @Provides
    internal fun provideMainPresenter(mainPresenter: MainPresenter<MainMVPView>)
            : MainMVPPresenter<MainMVPView> = mainPresenter

}