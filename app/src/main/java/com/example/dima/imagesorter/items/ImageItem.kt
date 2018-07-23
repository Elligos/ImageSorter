package com.example.dima.imagesorter.items

class ImageItem(val path : String?, val info : String = "") : RowItem() {

    override fun getItemType(): RowItemType {
        return RowItemType.IMAGE_ITEM
    }
}