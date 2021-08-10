package com.example.dima.imagesorter.items

import java.util.*

class ImageItem(val path : String?,
                val info : String = "",
                val size : Long = 0,
                val date : Date = Date(0)) : RowItem() {

    override fun getItemType(): RowItemType {
        return RowItemType.IMAGE_ITEM
    }
}