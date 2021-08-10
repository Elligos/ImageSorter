package com.example.dima.imagesorter.ui.groups.presenter


import com.example.dima.imagesorter.providers.AppPreferenceHelper
import com.example.dima.imagesorter.ui.base.presenter.BasePresenter
import com.example.dima.imagesorter.ui.groups.view.GroupPickerMVPView
import com.example.dima.imagesorter.util.log
import javax.inject.Inject

class GroupPickerPresenter <groupPickerView : GroupPickerMVPView> @javax.inject.Inject constructor():
                                    BasePresenter<groupPickerView>(),
                                    GroupPickerMVPPresenter<groupPickerView>
{
    override fun isMvpViewAttached(): Boolean = getView()!=null

    @Inject
    internal lateinit var prefHelper: AppPreferenceHelper


    override fun selectGrouping(group: Int) {
        getView()?.showMessage("Position = $group")
        "Group $group selected!".log()
        prefHelper.setItemsPooling(group)
    }

    override fun getGrouping(): Int {
        //return 1
        return prefHelper.getItemsPooling()?:0
    }


}