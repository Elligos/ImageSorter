package com.example.dima.imagesorter.ui.base.presenter

import com.example.dima.imagesorter.ui.base.view.MVPView


interface MVPPresenter <in V : MVPView>  {

    fun onAttach(mvpView: V?)

    fun onDetach()

    fun isMvpViewAttached() : Boolean

    fun getView() : MVPView?

}