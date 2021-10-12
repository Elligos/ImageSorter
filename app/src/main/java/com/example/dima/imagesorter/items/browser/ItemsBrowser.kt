package com.example.dima.imagesorter.items.browser

import com.example.dima.imagesorter.items.DirectoryItem
import com.example.dima.imagesorter.items.RowItem




interface ItemsBrowser {

    fun selectBrowserGroupMode(groupMode : GroupingMode)
    fun selectSorter()

    fun isRoot() : Boolean
    fun getRootItems() : ArrayList<RowItem>
    fun getDirectoryItemContent(item : DirectoryItem) : ArrayList<RowItem>
    fun getUpperLevelItems() : ArrayList<RowItem>



}