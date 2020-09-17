package com.example.dima.imagesorter.ui.images

import com.example.dima.imagesorter.ui.images.presenter.ItemsDisplayMVPPresenter
import com.example.dima.imagesorter.ui.images.presenter.ItemsDisplayPresenter
import com.example.dima.imagesorter.ui.images.view.ItemsDisplayMVPView
import dagger.Module
import dagger.Provides

@Module
class ImagesScrollFragmentModule {

    @Provides
    internal  fun provideImagesScrollPresenter(presenter : ItemsDisplayPresenter<ItemsDisplayMVPView>)
            : ItemsDisplayMVPPresenter<ItemsDisplayMVPView> = presenter


}