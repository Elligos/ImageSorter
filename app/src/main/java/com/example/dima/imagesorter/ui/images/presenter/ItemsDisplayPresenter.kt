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


    private fun selector(folderPath : String) : String = pathfinder.getFileName(folderPath)

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

        updateInitFolderPaths()
        val folderPaths = initFolderPaths

        folderPaths.sortBy { selector(it) }
        for(folderPath in folderPaths){
            val item = DirectoryItem(
                    path = folderPath,
                    directoryName = pathfinder.getFileName(folderPath)
            )
            items.add(item)
        }
        return items

    }

    override fun onItemClick(item : RowItem) {
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
                    directoryName = pathfinder.getFileName(folderPath)
            )
            items.add(directoryItem)
        }
        for(imagePath in imagePaths){
            val imageItem = ImageItem(
                    info = pathfinder.getFileName(imagePath),
                    path = imagePath,
                    size = pathfinder.getSize(imagePath),
                    date = pathfinder.getCreationDate(imagePath)
            )
            items.add(imageItem)
            "Image  ${imageItem.info} with: size = ${imageItem.size}, date = ${imageItem.date}  ready!".log()
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
            var parentDirectoryPath = pathfinder.getParentDirectoryPath(currentDirectoryPath)
            val item = DirectoryItem(
                    path = parentDirectoryPath,
                    directoryName = pathfinder.getFileName(parentDirectoryPath)
            )
            onDirectoryClick(item)
        }
    }


}