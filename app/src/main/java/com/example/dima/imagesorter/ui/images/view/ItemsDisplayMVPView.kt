package com.example.dima.imagesorter.ui.images.view

import com.example.dima.imagesorter.items.RowItem
import com.example.dima.imagesorter.ui.base.view.MVPView

interface ItemsDisplayMVPView : MVPView {
    fun displayItems(items: ArrayList<RowItem>)
    fun hideReturnButton()
    fun showReturnButton()
}