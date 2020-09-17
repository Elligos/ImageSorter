package com.example.dima.imagesorter.items

abstract class RowItem {
    abstract fun getItemType() : RowItemType

    enum class RowItemType{
        GROUP_TITLE_ITEM, IMAGE_ITEM, DIRECTORY_ITEM
    }
}