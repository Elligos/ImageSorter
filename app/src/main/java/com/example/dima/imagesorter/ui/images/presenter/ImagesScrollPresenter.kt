package com.example.dima.imagesorter.ui.images.presenter

import com.example.dima.imagesorter.ui.base.presenter.BasePresenter
import com.example.dima.imagesorter.ui.images.view.ImagesScrollMVPView
import javax.inject.Inject

class ImagesScrollPresenter<V : ImagesScrollMVPView> @Inject constructor(): BasePresenter<V>(), ImagesScrollMVPPresenter<V>{
    override fun isMvpViewAttached(): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return true
    }

    override fun onViewPrepared() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}