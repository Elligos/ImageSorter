package com.example.dima.imagesorter

import android.content.Context
import com.example.dima.imagesorter.items.RowItem

interface SorterContract {
    interface View : BaseView<Presenter>{
        fun showImages()
        fun showSortedImages(items : List<RowItem>)
    }

    interface Presenter{
        var currentFiltering : Int
        fun loadImages()
        fun sortByDate()
        fun sortBySize()
        fun sortBuName()
        fun sortByType()//photos, downloaded, what's up, viber, etc.
    }
}