package com.example.dima.imagesorter.ui.groups.presenter

import com.example.dima.imagesorter.ui.base.presenter.MVPPresenter
import com.example.dima.imagesorter.ui.groups.view.GroupPickerMVPView

interface GroupPickerMVPPresenter < groupPickerView : GroupPickerMVPView> : MVPPresenter<groupPickerView>{
    fun selectGrouping(group : Int)
    fun getGrouping() : Int
}