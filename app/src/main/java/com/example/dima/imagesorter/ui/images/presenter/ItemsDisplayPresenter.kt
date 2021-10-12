package com.example.dima.imagesorter.ui.images.presenter

import com.example.dima.imagesorter.items.DirectoryItem
import com.example.dima.imagesorter.items.GroupTitleItem
import com.example.dima.imagesorter.items.ImageItem
import com.example.dima.imagesorter.items.RowItem
import com.example.dima.imagesorter.items.browser.GROUP_BY_APP
import com.example.dima.imagesorter.items.browser.GROUP_BY_DATE
import com.example.dima.imagesorter.items.browser.GROUP_BY_SIZE
import com.example.dima.imagesorter.items.browser.ImageItemsBrowser
import com.example.dima.imagesorter.providers.AppPreferenceHelper
import com.example.dima.imagesorter.ui.base.presenter.BasePresenter
import com.example.dima.imagesorter.ui.images.view.ItemsDisplayMVPView
import com.example.dima.imagesorter.util.log
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


//adding an @Inject constructor() makes ItemsDisplayPresenter available to Dagger
class ItemsDisplayPresenter<V : ItemsDisplayMVPView>
        @Inject constructor(compositeDisposable: CompositeDisposable):
        BasePresenter<V>(compositeDisposable = compositeDisposable),
        ItemsDisplayMVPPresenter<V>{


    @Inject internal lateinit var prefHelper: AppPreferenceHelper
    @Inject lateinit var browser : ImageItemsBrowser

    private var isViewAttached = false

    private var viewState: V? = null

    override fun isMvpViewAttached(): Boolean {
        return isViewAttached
    }

    override fun attachView(view: ItemsDisplayMVPView) {
        viewState = getView()
        isViewAttached = true
    }

    override fun onViewPrepared() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var currentPolling : Int? = 0


    override fun updateInitFolderPaths(){
    }

    override fun setCurrentFolderPath(path : String){
    }

    override fun getCurrentFolderPath() : String{
        return ""
    }



    override fun init(){
        compositeDisposable.add(prefHelper.getItemsPooling().subscribe({
            currentPolling = it
            when (currentPolling) {
                0 -> browser.selectBrowserGroupMode(GROUP_BY_APP)
                1 -> browser.selectBrowserGroupMode(GROUP_BY_DATE)
                2 -> browser.selectBrowserGroupMode(GROUP_BY_SIZE)
                else -> browser.selectBrowserGroupMode(GROUP_BY_APP)
            }
            "Image  pooling  $it selected through rx in ItemsDisplayPresenter module.".log()
            returnToInitDirectories()
            "Return to upper directories through rx  with pooling  $it selected in ItemsDisplayPresenter module.".log()
        }, {
            "Image  pooling  $it selection failed through rx in ItemsDisplayPresenter module!".log()
        }))
    }

    override fun getInitItems(): ArrayList<RowItem> = browser.getRootItems()


    override fun returnToInitDirectories(){
        val items = browser.getRootItems()
        getView()?.displayItems(items)
        getView()?.hideReturnButton()
    }

    override fun onItemClick(item : RowItem) {
        when(item){
            is ImageItem -> onImageClick(item)
            is GroupTitleItem -> onTitleClick(item)
            is DirectoryItem -> onDirectoryClick(item)
        }
    }

    override fun onImageClick(item : ImageItem) {
        "Image ${item.info} pressed!".log()
    }

    override fun onTitleClick(item : GroupTitleItem) {
        "Title ${item.title} pressed!".log()
    }

    override fun onDirectoryClick(item : DirectoryItem) {
        val items = browser.getDirectoryItemContent(item)
        getView()?.displayItems(items)
        getView()?.showReturnButton()
    }

    override fun returnToUpperDirectory(){
        val items = browser.getUpperLevelItems()
        getView()?.displayItems(items)
        if(browser.isRoot()) getView()?.hideReturnButton() else getView()?.showReturnButton()
    }

}