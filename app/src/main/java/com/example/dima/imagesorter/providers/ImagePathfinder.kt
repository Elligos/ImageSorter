package com.example.dima.imagesorter.providers

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.math.abs

class ImagePathfinder{

        var parentContext: Context

        constructor(context: Context){
            parentContext = context
        }



    fun getImageFolderPathsFromExternal() : ArrayList<String>{
//        val cursor: Cursor?
//        val columnIndex: Int
//        val folderPaths = ArrayList<String>()
//        var imagePath: String?
//        var folderPath: String?
//        val URI: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        val IMAGE_PATH = MediaStore.MediaColumns.DATA
//
//        val projection = arrayOf(IMAGE_PATH)//select columns we need
//
//        cursor = parentContext.
//                contentResolver.
//                query(URI, projection, null, null, null)
//        //if cursor is empty, return empty list
//        if(!cursor.moveToFirst()) {
//            cursor.close()
//            return folderPaths
//        }
//        columnIndex = cursor.getColumnIndexOrThrow(IMAGE_PATH)
//        while (cursor.moveToNext()) {
//            imagePath = cursor.getString(columnIndex)
//            folderPath = File(imagePath).parent//remove image file name from path
//            folderPaths.add(folderPath)//add only directory path to list
//        }
//        cursor.close()

        val imagePaths = getImagePathsFromExternal()
        val folderPaths = ArrayList<String>()
        var folderPath: String?

        for(path in imagePaths){
            folderPath = File(path).parent//remove image file name from path
            folderPaths.add(folderPath)//add only directory path to list
        }

        removeDuplicates(folderPaths)
        removeSubdirectories(folderPaths)//leave only root image directories
        folderPaths.sort()
        for(path in folderPaths) {
            Log.i("ListingImages", " bucket=$path")
        }
        return folderPaths
    }

    fun getImagePathsFromExternal() : ArrayList<String>{
        val cursor: Cursor?
        val columnIndex: Int
        val images = ArrayList<String>()
        var imagePath: String?
        val URI: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val IMAGE_PATH = MediaStore.MediaColumns.DATA
        //select columns we need
        val projection = arrayOf(IMAGE_PATH)

        cursor = parentContext.
                contentResolver.
                query(URI, projection, null, null, null)
        //if cursor is empty, return empty list
        if(!cursor.moveToFirst()) {
            cursor.close()
            return images
        }
        columnIndex = cursor.getColumnIndexOrThrow(IMAGE_PATH)
        while (cursor.moveToNext()) {
            imagePath = cursor.getString(columnIndex)
            images.add(imagePath)//add image path to list
        }
        cursor.close()
        for(path in images) {
            Log.i("ListingImages", " bucket=$path")
        }
        return images
    }

    fun getImagePathsFromFolder(folderPath: String) : ArrayList<String>
    {
        val f = ArrayList<String>() // list of file paths
        val listFile: Array<File>
        val folder = File(folderPath)


        Log.i("Search in folder:", " $folderPath")

        if (folder.isDirectory) {
            listFile = folder.listFiles()
            for (file in listFile) {
                if(!isImage(file.extension)) continue
                f.add(file.absolutePath)
                Log.i("Found file:", " ${file.absolutePath}")
            }
        }
        return f
    }

    fun getSubdirectoryPathsFromFolder(folderPath: String) : ArrayList<String>
    {
        val f = ArrayList<String>() // list of file paths
        val listFile: Array<File>
        val folder = File(folderPath)


        Log.i("Search in folder:", " $folderPath")

        if (folder.isDirectory) {
            listFile = folder.listFiles()
            for (file in listFile) {
                if(!file.isDirectory) continue
                f.add(file.absolutePath)
                Log.i("Found directory:", " ${file.absolutePath}")
            }
        }
        return f
    }

    private fun isImage(extension : String) : Boolean
    {
        val imagePattern = ArrayList<String>()
        imagePattern.add("jpeg")
        imagePattern.add("jpg")
        imagePattern.add("png")
        var isImage = false
        for(pattern in imagePattern){
            if(extension.toLowerCase(Locale.ROOT) == pattern) isImage=true
        }
        return isImage
    }

    fun getFileName(absolutePath : String) : String{
        return absolutePath.substringAfterLast("/")
    }

    fun getCreationDate(absolutePath: String) : Date{

        return try {
            getCreationDateUnsafe(absolutePath)
        }
        catch (e : Exception){
            Date(0)
        }
    }

    private fun getCreationDateUnsafe(absolutePath: String) : Date{

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val path: Path =  Paths.get(absolutePath)
            val attr = Files.readAttributes(path, BasicFileAttributes::class.java)
            //attr.creationTime().toString()
            Date(attr.creationTime().toMillis())
        } else {
            val file = File(absolutePath)
            Date(file.lastModified())
        }
    }

    fun getSize(absolutePath: String) : Long{

        return try{
            getSizeUnsafe(absolutePath)
        }
        catch(e : Exception){
            0
        }
    }


    private fun getSizeUnsafe(absolutePath: String) : Long{

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val path: Path =  Paths.get(absolutePath)
            val attr = Files.readAttributes(path, BasicFileAttributes::class.java)
            attr.size()
        } else {
            val file = File(absolutePath)
            file.length()
        }
    }



    private fun removeDuplicates(list : ArrayList<String>)
    {
        val set = HashSet(list)
        list.clear()
        list.addAll(set)
    }

    private fun removeSubdirectories(pathList : ArrayList<String>)
    {
        var pathsToDelete = ArrayList<Int>()

        for( i in 0 until pathList.size){
            val path = pathList[i]
            for(j in 0 until pathList.size){
                if(isSubdirectory(subdirectoryPath=pathList[j], path=path)){
                    Log.i("ListingImages", " Path ${pathList[j]} have root path $path ")
                    pathsToDelete.add(j)
                    //pathList.removeAt(j)
                    //Log.i("ListingImages", " Path ${pathList[j]} removed ")
                }
            }
        }
        pathsToDelete.sortDescending()
        for(k in pathsToDelete){
            Log.i("ListingImages", " Path ${pathList[k]} removed ")
            pathList.removeAt(k)
        }
    }

    private fun isSubdirectory(subdirectoryPath: String, path: String) : Boolean
    {
        if(path == subdirectoryPath) return false
        if(path.length > subdirectoryPath.length) return false
        val subpath = subdirectoryPath.substring(0, path.length)
        Log.i("ListingImages", " Subpath $subpath and path $path ")
        if(subpath == path) return true
        return false
    }

    fun getParentDirectoryPath(currentDirectoryPath: String) : String{
        return currentDirectoryPath.substringBeforeLast("/", "")
    }

}