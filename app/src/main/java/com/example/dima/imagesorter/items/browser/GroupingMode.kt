package com.example.dima.imagesorter.items.browser

sealed class GroupingMode
object GROUP_BY_APP : GroupingMode()
object GROUP_BY_DATE : GroupingMode()
object GROUP_BY_SIZE : GroupingMode()