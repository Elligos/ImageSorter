package com.example.dima.imagesorter.ui.images.presenter

import com.example.dima.imagesorter.ui.base.presenter.MVPPresenter
import com.example.dima.imagesorter.ui.images.view.ImagesScrollMVPView

interface ImagesScrollMVPPresenter<V : ImagesScrollMVPView> : MVPPresenter<V> {

    fun onViewPrepared()
}