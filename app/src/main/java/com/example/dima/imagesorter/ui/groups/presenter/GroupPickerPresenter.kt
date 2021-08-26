package com.example.dima.imagesorter.ui.groups.presenter


import com.example.dima.imagesorter.providers.AppPreferenceHelper
import com.example.dima.imagesorter.ui.base.presenter.BasePresenter
import com.example.dima.imagesorter.ui.groups.view.GroupPickerMVPView
import com.example.dima.imagesorter.util.log
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class GroupPickerPresenter <groupPickerView : GroupPickerMVPView>
                        @Inject constructor(compositeDisposable: CompositeDisposable):
                        BasePresenter<groupPickerView>(compositeDisposable = compositeDisposable),
                        GroupPickerMVPPresenter<groupPickerView>{


    override fun isMvpViewAttached(): Boolean = getView()!=null

    @Inject internal lateinit var prefHelper: AppPreferenceHelper

    private var currentPolling : Int? = 0

    override fun init(){
        compositeDisposable.add(prefHelper.getItemsPooling().subscribe({
            currentPolling = it
            "Image  pooling  $it selected through rx in ItemsDisplayPresenter module.".log()
        }, {
            "Image  pooling  $it selection failed through rx in ItemsDisplayPresenter module!".log()
        }))
    }



    override fun selectGrouping(group: Int) {
        compositeDisposable.add(prefHelper.setItemsPooling(group).subscribe())
    }

    override fun getGrouping(): Int = currentPolling?:0



}