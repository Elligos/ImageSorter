package com.example.dima.imagesorter.items.browser

import com.example.dima.imagesorter.items.DirectoryItem
import com.example.dima.imagesorter.items.ImageItem
import com.example.dima.imagesorter.items.RowItem
import com.example.dima.imagesorter.providers.ImagePathfinder
import com.example.dima.imagesorter.util.log


class ImageItemsBrowser constructor(private val pathfinder: ImagePathfinder): ItemsBrowser {

    var grouping : GroupingMode = GROUP_BY_APP
    private var initFolderPaths  = ArrayList<String>()
    private var currentDirectoryPath = ""


    override fun selectBrowserGroupMode(groupMode: GroupingMode) {
        grouping = groupMode
    }

    override fun selectSorter() {
        TODO("Not yet implemented")
    }

    private fun selector(folderPath : String) : String = pathfinder.getFileName(folderPath)

    private fun updateInitFolderPaths(){
        initFolderPaths = pathfinder.getImageRootFolderPathsFromExternal()
    }

    private fun getImageItems(imagePaths : ArrayList<String>) : ArrayList<ImageItem>{
        val imageItems = ArrayList<ImageItem>()

        for(imagePath in imagePaths){
            val imageItem = ImageItem(
                    info = pathfinder.getFileName(imagePath),
                    path = imagePath,
                    size = pathfinder.getSize(imagePath),
                    date = pathfinder.getCreationDate(imagePath)
            )
            imageItems.add(imageItem)
            ("Image  ${imageItem.info} with: size = ${imageItem.size}, " +
                    "date = ${imageItem.date}  ready!").log()
        }
        return imageItems
    }

    private fun ImageItem.getRealYear() : Int{
        return this.date.year+1900
    }

    override fun isRoot(): Boolean {
        return currentDirectoryPath == ""
    }

    override fun getRootItems(): ArrayList<RowItem> {
        pathfinder.updateImagePaths()
        return when (grouping) {
            GROUP_BY_APP -> getAppsRootFolders()
            GROUP_BY_DATE -> getDateRootFolders()
            GROUP_BY_SIZE -> getSizeRootFolders()
        }
    }

    override fun getDirectoryItemContent(item : DirectoryItem): ArrayList<RowItem> {
        return when(grouping)
        {
            GROUP_BY_APP -> getAppDirectoryContent(item)
            GROUP_BY_DATE -> getDateDirectoryContent(item)
            GROUP_BY_SIZE -> getSizeDirectoryContent(item)
        }
    }

    override fun getUpperLevelItems(): ArrayList<RowItem> {
        return when(grouping){
            GROUP_BY_APP -> getAppUpperLevelItems()
            GROUP_BY_DATE -> getDateUpperLevelItems()
            GROUP_BY_SIZE -> getSizeUpperLevelItems()
        }
    }

    //---------------------------------Group by Apps----------------------------------------------//
    private fun getAppsRootFolders() : ArrayList<RowItem> {
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

    private fun getAppDirectoryContent(item : DirectoryItem) : ArrayList<RowItem>  {
        "Directory ${item.directoryName} pressed!".log()
        val items = ArrayList<RowItem>()
        val directoryPath = item.path ?: return items// if path is null return empty list
        val imagePaths = pathfinder.getImagePathsFromFolder(directoryPath)

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
            ("Image  ${imageItem.info} with: size = ${imageItem.size}, " +
                    "date = ${imageItem.date}  ready!").log()
        }
        currentDirectoryPath = item.path
        "Images from directory ${item.directoryName} ready!".log()
        return items
    }

    private fun getAppUpperLevelItems() : ArrayList<RowItem> {
        var isInitFolder = false
        for(initFolderPath in initFolderPaths) {
            if (currentDirectoryPath == initFolderPath) {
                isInitFolder = true
                break
            }
        }
        if(isInitFolder){
            currentDirectoryPath = ""
            return getAppsRootFolders()
        }

        val parentDirectoryPath = pathfinder.getParentDirectoryPath(currentDirectoryPath)
        val item = DirectoryItem(
                path = parentDirectoryPath,
                directoryName = pathfinder.getFileName(parentDirectoryPath)
        )
        return getAppDirectoryContent(item)

    }
    //---------------------------------Group by Apps-End------------------------------------------//

    //---------------------------------Group by Date----------------------------------------------//
    private fun getDateRootFolders() : ArrayList<RowItem> {
        val imagePaths = pathfinder.getImagePathsFromExternal()
        val imageItems = getImageItems(imagePaths)

        //Make directories with image creation dates. Use year as root directory name
        val uniqueNames = HashSet<String>()
        var dateFolders = ArrayList<DirectoryItem>()
        for(item in imageItems){
            val item = DirectoryItem(
                    path = item.getRealYear().toString(),
                    directoryName = item.getRealYear().toString()
            )
            if(uniqueNames.add(item.directoryName))  dateFolders.add(item)
        }
        dateFolders.sortBy { it.directoryName }


        return ArrayList(dateFolders)
    }

    private fun getDateDirectoryContent(directoryItem : DirectoryItem) : ArrayList<RowItem>  {
        return if(isYearDirectoryItem(directoryItem)) getYearDirectoryContent(directoryItem)
        else getMonthDirectoryContent(directoryItem)
    }

    private fun isYearDirectoryItem(directoryItem: DirectoryItem) : Boolean{
        if(directoryItem.path?.
                substringAfter("/", "root path") == "root path")
            return true
        return false
    }

    private fun getYearDirectoryContent(directoryItem : DirectoryItem) : ArrayList<RowItem> {
        val monthDirectoryItems = ArrayList<RowItem>()
        if(directoryItem.path == null) return monthDirectoryItems// return empty list

        val allImagePaths = pathfinder.getCachedImagePaths()
        val allImageItems = getImageItems(allImagePaths)

        val uniqueNames = HashSet<String>()
        for(imageItem in allImageItems){
            // Check if an image item belong to the current year directory
            if(imageItem.getRealYear().toString() != directoryItem.directoryName) continue
            // Create a month directory for image
            val item = DirectoryItem(
                    path = "${directoryItem.path}/${imageItem.date.month}",
                    directoryName = convertIntToMonthName(imageItem.date.month)
            )
            if(uniqueNames.add(item.directoryName))  monthDirectoryItems.add(item)
        }

        currentDirectoryPath = directoryItem.path
        "Images from directory ${directoryItem.directoryName} ready!".log()

        return monthDirectoryItems
    }

    private fun convertIntToMonthName(month : Int) : String{
        return when(month){
            0 -> "January"
            1 -> "February"
            2 -> "March"
            3 -> "April"
            4 -> "May"
            5 -> "June"
            6 -> "July"
            7 -> "August"
            8 -> "September"
            9 -> "October"
            10 -> "November"
            11 -> "December"
            else -> ""
        }
    }

    private fun getMonthDirectoryContent(directoryItem : DirectoryItem) : ArrayList<RowItem> {
        val monthImageItems = ArrayList<RowItem>()
        if(directoryItem.path == null) return monthImageItems// return empty list

        val allImagePaths = pathfinder.getCachedImagePaths()
        val allImageItems = getImageItems(allImagePaths)

        for(imageItem in allImageItems){
            // Check if an image item belong to the current year directory
            if(imageItem.getRealYear().toString() !=
                    directoryItem.path.substringBefore("/", ""))
                continue
            // Check if an image item belong to the current month directory
            if(imageItem.date.month.toString() !=
                    directoryItem.path.substringAfter("/", ""))
                continue
            monthImageItems.add(imageItem)
        }

        currentDirectoryPath = directoryItem.path
        "Images from directory ${directoryItem.directoryName} ready!".log()
        return monthImageItems
    }

    private fun getDateUpperLevelItems() : ArrayList<RowItem> {
        if(currentDirectoryPath.
                substringAfter("/", "no upper directory")
                        == "no upper directory")
        {
            currentDirectoryPath = ""
            return getDateRootFolders()
        }

        val parentDirectoryPath = currentDirectoryPath.
                                    substringBefore("/", "oops!")
        val item = DirectoryItem(
                path = parentDirectoryPath,
                directoryName = parentDirectoryPath
        )
        return getYearDirectoryContent(item)

    }
    //---------------------------------Group by Date-End------------------------------------------//

    //---------------------------------Group by Size----------------------------------------------//
    private fun getSizeRootFolders() : ArrayList<RowItem>{
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

    private fun getSizeDirectoryContent(directoryItem : DirectoryItem) : ArrayList<RowItem>{
        return when(directoryItem.directoryName){
            "Small" -> getSmallImageItems()
            "Medium" -> getMediumImageItems()
            "Large" -> getLargeImageItems()
            else -> getMediumImageItems()
        }
    }

    private fun getSmallImageItems() : ArrayList<RowItem>{
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

    private fun getMediumImageItems() : ArrayList<RowItem>{
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

    private fun getLargeImageItems() : ArrayList<RowItem>{
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

    private fun getSizeUpperLevelItems() : ArrayList<RowItem> {
        currentDirectoryPath = ""
        return getSizeRootFolders()
    }
    //---------------------------------Group by Size-End------------------------------------------//

}