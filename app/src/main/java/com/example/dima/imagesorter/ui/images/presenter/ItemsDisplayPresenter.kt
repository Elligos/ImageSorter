package com.example.dima.imagesorter.ui.images.presenter

import com.example.dima.imagesorter.items.DirectoryItem
import com.example.dima.imagesorter.items.GroupTitleItem
import com.example.dima.imagesorter.items.ImageItem
import com.example.dima.imagesorter.items.RowItem
import com.example.dima.imagesorter.providers.ImagePathfinder
import com.example.dima.imagesorter.ui.base.presenter.BasePresenter
import com.example.dima.imagesorter.ui.images.view.ItemsDisplayMVPView
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

    private var initFolderPaths  = ArrayList<String>()
    private var currentDirectoryPath = ""


    private fun selector(folderPath : String) : String = pathfinder.getFolderName(folderPath)

    override fun updateInitFolderPaths(){
        initFolderPaths = pathfinder.getImageFolderPathsFromExternal()
    }

    override fun setCurrentFolderPath(path : String){
        currentDirectoryPath = path
    }

    override fun getCurrentFolderPath() : String{
        return currentDirectoryPath
    }

    override fun getInitItems(): ArrayList<RowItem> {
        val items = ArrayList<RowItem>()

 //       val folderPaths = pathfinder.getImageFolderPathsFromExternal()
        updateInitFolderPaths()
        val folderPaths = initFolderPaths

        folderPaths.sortBy { selector(it) }
        for(folderPath in folderPaths){
            val item = DirectoryItem(
                    path = folderPath,
                    directoryName = pathfinder.getFolderName(folderPath)
            )
            items.add(item)
        }
        return items

//        val item1 = GroupTitleItem(
//                "2020 June"
//        )
//        val item2 = GroupTitleItem(
//                "WhatsUp"
//        )
//        val item3 = DirectoryItem(
//                "",
//                "Directory"
//        )
//        items.add(item1)
//        items.add(item2)
//        items.add(item3)
//        items.add(item2)
//        items.add(item3)
//        items.add(item3)
//        items.add(item3)
//        items.add(item3)
//        items.add(item3)
//        items.add(item2)
////        val imagesPath = getListOfImagesPath(parentContext)
//        val imagesPath = pathfinder.getImagePathsFromExternal()
//
////        getListOfImageBuckets(parentContext)
////        ImagePathfinder.getImageFoldersFromExternal(parentContext)
////        ImagePathfinder.getImageFolderPathsFromExternal(parentContext)
//        pathfinder.getImageFoldersFromExternal()
//        var folderPaths = pathfinder.getImageFolderPathsFromExternal()
//        for(folder in folderPaths){
//            pathfinder.getImagePathsFromFolder(folder)
//        }
////        for(imagePath in imagesPath){
////            val item = GroupTitleItem(
////                    title = imagePath
////            )
////            items.add(item)
////        }
//        for(imagePath in imagesPath){
//            val item = ImageItem(
//                    info = imagePath,
//                    path = imagePath
//            )
//            items.add(item)
//        }
//
//        return items
    }

    override fun onItemClick(item : RowItem) {
//        when(item.getItemType()){
//            RowItem.RowItemType.IMAGE_ITEM -> "Image item pressed!".log()
//            RowItem.RowItemType.GROUP_TITLE_ITEM -> "Title item pressed!".log()
//            RowItem.RowItemType.DIRECTORY_ITEM -> "Directory item pressed!".log()
//        }
        when(item){
            is ImageItem -> onItemClick(item)
            is GroupTitleItem -> onTitleClick(item)
            is DirectoryItem -> onDirectoryClick(item)
        }

    }

    override fun onDirectoryClick(item : DirectoryItem) {
        "Directory ${item.directoryName} pressed!".log()
        val directoryPath = item.path ?: return
        val imagePaths = pathfinder.getImagePathsFromFolder(directoryPath)
        val items = ArrayList<RowItem>()
        val folderPaths = pathfinder.getSubdirectoryPathsFromFolder(directoryPath)
        folderPaths.sortBy { selector(it) }
        for(folderPath in folderPaths){
            val directoryItem = DirectoryItem(
                    path = folderPath,
                    directoryName = pathfinder.getFolderName(folderPath)
            )
            items.add(directoryItem)
        }
        for(imagePath in imagePaths){
            val imageItem = ImageItem(
                    info = pathfinder.getFolderName(imagePath),
                    path = imagePath
            )
            items.add(imageItem)
        }
        setCurrentFolderPath(item.path)
        "Images from directory ${item.directoryName} ready!".log()
        getView()?.displayItems(items)
        getView()?.showReturnButton()
    }

    override fun onImageClick(item : ImageItem) {
        "Image ${item.info} pressed!".log()
    }

    override fun onTitleClick(item : GroupTitleItem) {
        "Title ${item.title} pressed!".log()
    }

    override fun returnToInitDirectories() {
        val items = getInitItems()
        getView()?.displayItems(items)
        getView()?.hideReturnButton()
        setCurrentFolderPath("")
    }

    override fun returnToUpperDirectory()
    {

        var isInitFolder : Boolean = false
        for(initFolderPath in initFolderPaths) {
            if (currentDirectoryPath == initFolderPath) {
                isInitFolder = true
                break
            }
        }
        if(isInitFolder){
            returnToInitDirectories()
        }
        else{
            var parentDirectoryPath = ImagePathfinder.getParentDirectoryPath(currentDirectoryPath)
            val item = DirectoryItem(
                    path = parentDirectoryPath,
                    directoryName = pathfinder.getFolderName(parentDirectoryPath)
            )
            onDirectoryClick(item)
        }
    }


}