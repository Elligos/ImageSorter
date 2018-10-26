package com.example.dima.imagesorter.ui

import com.example.dima.imagesorter.items.RowItem
import com.example.dima.imagesorter.ui.base.presenter.MVPPresenter
import com.example.dima.imagesorter.ui.base.view.MVPView

interface SorterContract {
    interface View : MVPView {
        fun showImages()
        fun showSortedImages(items : List<RowItem>)
    }

    interface Presenter : MVPPresenter<MVPView> {
        var currentFiltering : Int
        fun loadImages()
        fun sortByDate()
        fun sortBySize()
        fun sortBuName()
        fun sortByType()//photos, downloaded, what's up, viber, etc.
    }
}