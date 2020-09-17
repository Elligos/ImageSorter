package com.example.dima.imagesorter.items

class DirectoryItem(val path: String?, val directoryName: String = "") : RowItem() {

    override fun getItemType(): RowItemType {
        return RowItemType.DIRECTORY_ITEM
    }
}