package com.example.dima.imagesorter.ui.images

import com.example.dima.imagesorter.ui.images.presenter.ImagesScrollMVPPresenter
import com.example.dima.imagesorter.ui.images.presenter.ImagesScrollPresenter
import com.example.dima.imagesorter.ui.images.view.ImagesScrollMVPView
import dagger.Module
import dagger.Provides

@Module
class ImagesScrollFragmentModule {

    @Provides
    internal  fun provideImagesScrollPresenter(presenter : ImagesScrollPresenter<ImagesScrollMVPView>)
            : ImagesScrollMVPPresenter<ImagesScrollMVPView> = presenter


}