package com.example.dima.imagesorter.ui

import com.example.dima.imagesorter.ui.base.view.MVPView


class SorterPresenter(val view: SorterContract.View) : SorterContract.Presenter{
    override fun onAttach(mvpView: MVPView?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getView(): MVPView? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var viewAttached = false

    override var currentFiltering: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override fun loadImages() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sortByDate() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sortBySize() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sortBuName() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sortByType() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDetach() {
        viewAttached = false
    }

    override fun isMvpViewAttached(): Boolean {
        return viewAttached
    }


}