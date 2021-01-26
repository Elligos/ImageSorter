package com.example.dima.imagesorter.ui.images.presenter

import com.example.dima.imagesorter.items.DirectoryItem
import com.example.dima.imagesorter.items.GroupTitleItem
import com.example.dima.imagesorter.items.ImageItem
import com.example.dima.imagesorter.items.RowItem
import com.example.dima.imagesorter.ui.base.presenter.MVPPresenter
import com.example.dima.imagesorter.ui.images.view.ItemsDisplayMVPView

interface ItemsDisplayMVPPresenter<V : ItemsDisplayMVPView> : MVPPresenter<V> {

    fun onViewPrepared()
    fun attachView(view : ItemsDisplayMVPView)
    fun getInitItems() : ArrayList<RowItem>
    fun onItemClick(item : RowItem)
    fun onDirectoryClick(item : DirectoryItem)
    fun onImageClick(item : ImageItem)
    fun onTitleClick(item : GroupTitleItem)
    fun returnToUpperDirectory()
    fun returnToInitDirectories()

    fun updateInitFolderPaths()
    fun setCurrentFolderPath(path : String)
    fun getCurrentFolderPath() : String
}