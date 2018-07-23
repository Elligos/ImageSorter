package com.example.dima.imagesorter.items

class GroupTitleItem(var title: String = "DEFAULT TITLE") : RowItem() {
    override fun getItemType(): RowItemType {
        return RowItemType.GROUP_TITLE_ITEM
    }
}