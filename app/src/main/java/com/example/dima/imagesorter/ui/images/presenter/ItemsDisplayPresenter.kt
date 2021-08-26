package com.example.dima.imagesorter.ui.images.presenter

import com.example.dima.imagesorter.items.DirectoryItem
import com.example.dima.imagesorter.items.GroupTitleItem
import com.example.dima.imagesorter.items.ImageItem
import com.example.dima.imagesorter.items.RowItem
import com.example.dima.imagesorter.providers.AppPreferenceHelper
import com.example.dima.imagesorter.providers.ImagePathfinder
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

    @Inject lateinit var pathfinder: ImagePathfinder
    @Inject internal lateinit var prefHelper: AppPreferenceHelper

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
    private var currentPolling : Int? = 0


    private fun selector(folderPath : String) : String = pathfinder.getFileName(folderPath)


    override fun updateInitFolderPaths(){
        initFolderPaths = pathfinder.getImageRootFolderPathsFromExternal()
    }

    override fun setCurrentFolderPath(path : String){
        currentDirectoryPath = path
    }

    override fun getCurrentFolderPath() : String{
        return currentDirectoryPath
    }

    override fun init(){
        pathfinder.updateImagePaths()
        compositeDisposable.add(prefHelper.getItemsPooling().subscribe({
            currentPolling = it
            "Image  pooling  $it selected through rx in ItemsDisplayPresenter module.".log()
            returnToInitDirectories()
            "Return to upper directories through rx  with pooling  $it selected in ItemsDisplayPresenter module.".log()
        }, {
            "Image  pooling  $it selection failed through rx in ItemsDisplayPresenter module!".log()
        }))
    }

    override fun getInitItems(): ArrayList<RowItem> {
        return when (currentPolling) {
            0 -> getAppsRootFolders()
            1 -> getDateRootFolders()
            2 -> getSizeRootFolders()
            else -> getAppsRootFolders()//TODO: replace 0,1,2 with sealed class
        }
    }

    fun getImageItems(imagePaths : ArrayList<String>) : ArrayList<ImageItem>{
        val imageItems = ArrayList<ImageItem>()

        for(imagePath in imagePaths){
            val imageItem = ImageItem(
                    info = pathfinder.getFileName(imagePath),
                    path = imagePath,
                    size = pathfinder.getSize(imagePath),
                    date = pathfinder.getCreationDate(imagePath)
            )
            imageItems.add(imageItem)
            "Image  ${imageItem.info} with: size = ${imageItem.size}, date = ${imageItem.date}  ready!".log()
        }
        return imageItems
    }

    fun getAppsRootFolders() : ArrayList<RowItem> {
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

    fun getDateRootFolders() : ArrayList<RowItem> {
        var imagePaths = pathfinder.getImagePathsFromExternal()
        val imageItems = getImageItems(imagePaths)

        //Make directories with image creation dates. Use year as root directory name
        val uniqueNames = HashSet<String>()
        var dateFolders = ArrayList<DirectoryItem>()
        for(item in imageItems){
            val item = DirectoryItem(
                    path = (item.date.year+1900).toString(),
                    directoryName = (item.date.year+1900).toString()
            )
            if(uniqueNames.add(item.directoryName))  dateFolders.add(item)
        }
        dateFolders.sortBy { it.directoryName }


        return ArrayList(dateFolders)
    }

    fun getSizeRootFolders() : ArrayList<RowItem>{
        val items = ArrayList<RowItem>()

        val smallImageDirectoryItem = DirectoryItem(
                path = "",
                directoryName = "Small"
        )
        items.add(smallImageDirectoryItem)

        val mediumImageDirectoryItem = DirectoryItem(
                path = "",
                directoryName = "Medium"
        )
        items.add(mediumImageDirectoryItem)

        val largeImageDirectoryItem = DirectoryItem(
                path = "",
                directoryName = "Large"
        )
        items.add(largeImageDirectoryItem)

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
        when(currentPolling)
        {
            0 -> onAppDirectoryClick(item)
            1 -> onDateDirectoryClick(item)
            2 -> onSizeDirectoryClick(item)
            else -> onAppDirectoryClick(item)
        }
    }

    fun onAppDirectoryClick(item : DirectoryItem) {
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

    fun onDateDirectoryClick(directoryItem : DirectoryItem){
        if(isYearDirectoryItem(directoryItem)) onYearDirectoryClick(directoryItem)
        else onMonthDirectoryClick(directoryItem)
    }

    fun isYearDirectoryItem(directoryItem: DirectoryItem) : Boolean{
        if(directoryItem.path?.substringAfter("/", "no substring") == "no substring") return true
        return false
    }

    fun onYearDirectoryClick(directoryItem : DirectoryItem){
        if(directoryItem.path == null) return

        val allImagePaths = pathfinder.getCachedImagePaths()
        val monthDirectoryItems = ArrayList<RowItem>()
        val allImageItems = getImageItems(allImagePaths)

        val uniqueNames = HashSet<String>()
        for(imageItem in allImageItems){
            if((imageItem.date.year+1900).toString() != directoryItem.directoryName) continue
            val item = DirectoryItem(
                    path = "${directoryItem.path}/${imageItem.date.month}",
                    directoryName = (imageItem.date.month).toString()
            )
            if(uniqueNames.add(item.directoryName))  monthDirectoryItems.add(item)
        }

        setCurrentFolderPath(directoryItem.path)
        "Images from directory ${directoryItem.directoryName} ready!".log()
        getView()?.displayItems(monthDirectoryItems)
        getView()?.showReturnButton()

    }

    fun onMonthDirectoryClick(directoryItem : DirectoryItem){
        if(directoryItem.path == null) return

        val allImagePaths = pathfinder.getCachedImagePaths()
        val monthImageItems = ArrayList<RowItem>()
        val allImageItems = getImageItems(allImagePaths)

        for(imageItem in allImageItems){
            if((imageItem.date.year+1900).toString() != directoryItem.path.substringBefore("/", "")) continue
            if(imageItem.date.month.toString() != directoryItem.directoryName) continue
            monthImageItems.add(imageItem)
        }

        setCurrentFolderPath(directoryItem.path)
        "Images from directory ${directoryItem.directoryName} ready!".log()
        getView()?.displayItems(monthImageItems)
        getView()?.showReturnButton()
    }


    fun onSizeDirectoryClick(directoryItem : DirectoryItem){
        val directoryImageItems = when(directoryItem.directoryName){
            "Small" ->  getSmallImageItems()
            "Medium" -> getMediumImageItems()
            "Large" -> getLargeImageItems()
            else -> getMediumImageItems()
        }
        getView()?.displayItems(directoryImageItems)
        getView()?.showReturnButton()
    }

    fun getSmallImageItems() : ArrayList<RowItem>{
        val allImagePaths = pathfinder.getCachedImagePaths()
        val allImageItems = getImageItems(allImagePaths)
        val smallImageItems = ArrayList<RowItem>()

        val smallImageMaxSize = 1024*1024
        for(imageItem in allImageItems){
            if(imageItem.size > smallImageMaxSize) continue
            smallImageItems.add(imageItem)
        }

        return smallImageItems
    }

    fun getMediumImageItems() : ArrayList<RowItem>{
        val allImagePaths = pathfinder.getCachedImagePaths()
        val allImageItems = getImageItems(allImagePaths)
        val mediumImageItems = ArrayList<RowItem>()

        val smallImageMaxSize = 1024*1024
        val mediumImageMaxSize = 6*1024*1024

        for(imageItem in allImageItems){
            if(imageItem.size <= smallImageMaxSize) continue
            if(imageItem.size > mediumImageMaxSize) continue
            mediumImageItems.add(imageItem)
        }

        return mediumImageItems

    }

    fun getLargeImageItems() : ArrayList<RowItem>{
        val allImagePaths = pathfinder.getCachedImagePaths()
        val allImageItems = getImageItems(allImagePaths)
        val largeImageItems = ArrayList<RowItem>()

        val mediumImageMaxSize = 6*1024*1024

        for(imageItem in allImageItems){
            if(imageItem.size <= mediumImageMaxSize) continue
            largeImageItems.add(imageItem)
        }

        return largeImageItems

    }

    override fun onImageClick(item : ImageItem) {
        "Image ${item.info} pressed!".log()
    }

    override fun onTitleClick(item : GroupTitleItem) {
        "Title ${item.title} pressed!".log()
    }

    override fun returnToInitDirectories(){
        setCurrentFolderPath("")
        val items = getInitItems()
        getView()?.displayItems(items)
        getView()?.hideReturnButton()
    }

    override fun returnToUpperDirectory(){
        when(currentPolling){
            0 -> returnToUpperAppDirectory()
            1 -> returnToUpperDateDirectory()
            2 -> returnToUpperSizeDirectory()
            else -> returnToUpperAppDirectory()
        }
    }

    fun returnToUpperAppDirectory(){
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

    fun returnToUpperDateDirectory(){
        if(currentDirectoryPath.substringAfter("/", "no substring") == "no substring"){
            returnToInitDirectories()
            return
        }

        var parentDirectoryPath = currentDirectoryPath.substringBefore("/", "no substring")
        val item = DirectoryItem(
                path = parentDirectoryPath,
                directoryName = parentDirectoryPath
        )
        onYearDirectoryClick(item)

    }

    fun returnToUpperSizeDirectory(){
        returnToInitDirectories()
    }


}