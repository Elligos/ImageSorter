package com.example.dima.imagesorter.ui.images.presenter

import com.example.dima.imagesorter.items.DirectoryItem
import com.example.dima.imagesorter.items.GroupTitleItem
import com.example.dima.imagesorter.items.ImageItem
import com.example.dima.imagesorter.items.RowItem
import com.example.dima.imagesorter.providers.ImagePathfinder
import com.example.dima.imagesorter.ui.base.presenter.BasePresenter
import com.example.dima.imagesorter.ui.images.view.ItemsDisplayMVPView
import com.example.dima.imagesorter.util.getListOfImagesPath
import com.example.dima.imagesorter.util.log
import javax.inject.Inject

//adding an @Inject constructor() makes ItemsDisplayPresenter available to Dagger
class ItemsDisplayPresenter<V : ItemsDisplayMVPView> @Inject constructor(): BasePresenter<V>(), ItemsDisplayMVPPresenter<V>{

    @Inject lateinit var pathfinder: ImagePathfinder

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



    override fun getItems(): ArrayList<RowItem> {
        var items = ArrayList<RowItem>()

        val item1 = GroupTitleItem(
                "2020 June"
        )
        val item2 = GroupTitleItem(
                "WhatsUp"
        )
        val item3 = DirectoryItem(
                "",
                "Directory"
        )
        items.add(item1)
        items.add(item2)
        items.add(item3)
        items.add(item2)
        items.add(item3)
        items.add(item3)
        items.add(item3)
        items.add(item3)
        items.add(item3)
        items.add(item2)
//        val imagesPath = getListOfImagesPath(parentContext)
        val imagesPath = pathfinder.getImagePathsFromExternal()

//        getListOfImageBuckets(parentContext)
//        ImagePathfinder.getImageFoldersFromExternal(parentContext)
//        ImagePathfinder.getImageFolderPathsFromExternal(parentContext)
        pathfinder.getImageFoldersFromExternal()
        var folderPaths = pathfinder.getImageFolderPathsFromExternal()
        for(folder in folderPaths){
            pathfinder.getImagePathsFromFolder(folder)
        }
//        for(imagePath in imagesPath){
//            val item = GroupTitleItem(
//                    title = imagePath
//            )
//            items.add(item)
//        }
        for(imagePath in imagesPath){
            val item = ImageItem(
                    info = imagePath,
                    path = imagePath
            )
            items.add(item)
        }

        return items
    }

    override fun onItemClick(item : RowItem) {
        when(item.getItemType()){
            RowItem.RowItemType.IMAGE_ITEM -> "Image item pressed!".log()
            RowItem.RowItemType.GROUP_TITLE_ITEM -> "Title item pressed!".log()
            RowItem.RowItemType.DIRECTORY_ITEM -> "Directory item pressed!".log()
        }

    }

    override fun onDirectoryClick() {
        TODO("Not yet implemented")

    }

    override fun onImageClick() {
        TODO("Not yet implemented")
    }

    override fun onTitleClick() {
        TODO("Not yet implemented")
    }


}