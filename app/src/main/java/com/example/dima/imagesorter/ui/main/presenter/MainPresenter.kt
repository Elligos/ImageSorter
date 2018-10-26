package com.example.dima.imagesorter.ui.main.presenter

import com.example.dima.imagesorter.ui.base.presenter.BasePresenter
import com.example.dima.imagesorter.ui.base.view.MVPView
import com.example.dima.imagesorter.ui.main.view.MainMVPView
import javax.inject.Inject


class MainPresenter<mainView : MainMVPView> @Inject internal constructor() : BasePresenter<mainView>(), MainMVPPresenter<mainView>{


    override fun onAttach(mvpView: mainView?) {
        super.onAttach(mvpView)
    }


    override fun onDetach() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isMvpViewAttached(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun getView(): mainView? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}