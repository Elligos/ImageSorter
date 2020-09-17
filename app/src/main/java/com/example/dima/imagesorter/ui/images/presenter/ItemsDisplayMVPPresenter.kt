package com.example.dima.imagesorter.ui.images.presenter

import com.example.dima.imagesorter.items.RowItem
import com.example.dima.imagesorter.ui.base.presenter.MVPPresenter
import com.example.dima.imagesorter.ui.images.view.ItemsDisplayMVPView

interface ItemsDisplayMVPPresenter<V : ItemsDisplayMVPView> : MVPPresenter<V> {

    fun onViewPrepared()
    fun attachView(view : ItemsDisplayMVPView)
    fun getItems() : ArrayList<RowItem>
    fun onItemClick(item : RowItem)
    fun onDirectoryClick()
    fun onImageClick()
    fun onTitleClick()
}