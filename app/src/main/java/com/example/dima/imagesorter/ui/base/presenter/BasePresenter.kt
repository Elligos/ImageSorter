package com.example.dima.imagesorter.ui.base.presenter

import com.example.dima.imagesorter.ui.base.view.MVPView
import io.reactivex.disposables.CompositeDisposable

abstract  class BasePresenter<V : MVPView> internal constructor(protected val compositeDisposable: CompositeDisposable) : MVPPresenter<V> {

    private var view: V? = null
    private val isViewAttached: Boolean get() = view != null

    override fun onAttach(view : V?){
        this.view = view
    }

    override fun getView() : V? = view

    override fun onDetach() {
        compositeDisposable.dispose()
        view = null
    }

}