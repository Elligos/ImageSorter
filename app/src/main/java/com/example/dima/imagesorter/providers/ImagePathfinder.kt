package com.example.dima.imagesorter.providers

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class ImagePathfinder{

        var parentContext: Context

        constructor(context: Context){
            parentContext = context
        }

    fun getImageFoldersFromExternal() : ArrayList<String>{
        val cursor: Cursor?
        val columnIndexData: Int
        val folders = ArrayList<String>()
        var folder: String?
        val EXTERNAL_CONTENT_URI: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val BUCKET_DISPLAY_NAME = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME
        //select column with image containers name
        val projection = arrayOf(BUCKET_DISPLAY_NAME)
        //get cursor
        cursor = parentContext.
                contentResolver.
                query(EXTERNAL_CONTENT_URI,
                        projection,
                        null,
                        null,
                        null)
        //if cursor is empty, return empty list
        if(!cursor.moveToFirst()) {
            cursor.close()
            return folders
        }
        columnIndexData = cursor.getColumnIndexOrThrow(BUCKET_DISPLAY_NAME)
        while (cursor.moveToNext()) {
            folder = cursor.getString(columnIndexData)
            folders.add(folder)
        }
        cursor.close()
        //delete dublicates and sort
        removeDuplicates(folders)
        folders.sort()
        for(directory in folders) {
            Log.i("ListingImageDirectories", " bucket=$directory")
        }
        return folders
    }

    fun getImageFolderPathsFromExternal() : ArrayList<String>{
        val cursor: Cursor?
        val columnIndex: Int
        val folderPaths = ArrayList<String>()
        var imagePath: String?
        var folderPath: String?
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
            return folderPaths
        }
        columnIndex = cursor.getColumnIndexOrThrow(IMAGE_PATH)
        while (cursor.moveToNext()) {
            imagePath = cursor.getString(columnIndex)
            folderPath = File(imagePath).parent//remove image file name from path
            folderPaths.add(folderPath)//add only directory path to list
        }
        cursor.close()
        removeDuplicates(folderPaths)
        removeSubdirectories(folderPaths)
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

    fun getFolderName(absolutePath : String) : String{
        return absolutePath.substringAfterLast("/")
    }

        companion object{
            fun getImageFoldersFromExternal(parentContext : Context) : ArrayList<String>{
                val cursor: Cursor?
                val columnIndexData: Int
                val folders = ArrayList<String>()
                var folder: String?
                val EXTERNAL_CONTENT_URI: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                val BUCKET_DISPLAY_NAME = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME
                //select column with image containers name
                val projection = arrayOf(BUCKET_DISPLAY_NAME)
                //get cursor
                cursor = parentContext.
                         contentResolver.
                         query(EXTERNAL_CONTENT_URI,
                                 projection,
                                 null,
                                 null,
                                 null)
                //if cursor is empty, return empty list
                if(!cursor.moveToFirst()) {
                    cursor.close()
                    return folders
                }
                columnIndexData = cursor.getColumnIndexOrThrow(BUCKET_DISPLAY_NAME)
                while (cursor.moveToNext()) {
                    folder = cursor.getString(columnIndexData)
                    folders.add(folder)
                }
                cursor.close()
                //delete dublicates and sort
                removeDuplicates(folders)
                folders.sort()
                for(directory in folders) {
                    Log.i("ListingImageDirectories", " bucket=$directory")
                }
                return folders
            }

            fun getImageFolderPathsFromExternal(parentContext : Context) : ArrayList<String>{
                val cursor: Cursor?
                val columnIndex: Int
                val folderPaths = ArrayList<String>()
                var imagePath: String?
                var folderPath: String?
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
                    return folderPaths
                }
                columnIndex = cursor.getColumnIndexOrThrow(IMAGE_PATH)
                while (cursor.moveToNext()) {
                    imagePath = cursor.getString(columnIndex)
                    folderPath = File(imagePath).parent//remove image file name from path
                    folderPaths.add(folderPath)//add only directory path to list
                }
                cursor.close()
                removeDuplicates(folderPaths)
                folderPaths.sort()
                for(path in folderPaths) {
                    Log.i("ListingImages", " bucket=$path")
                }
                return folderPaths
            }

            fun getImagePathsFromExternal(parentContext : Context) : ArrayList<String>{
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

            private fun removeDuplicates(list : ArrayList<String>)
            {
                var set = HashSet(list)
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

            fun isSubdirectory(subdirectoryPath: String, path: String) : Boolean
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

            private fun findCommonDirPath(paths: List<String>, separator: Char): String {
                if (paths.isEmpty()) return ""
                if (paths.size == 1) return paths[0]
                val splits = paths[0].split(separator)
                val n = splits.size
                val paths2 = paths.drop(1)
                var k = 0
                var common = ""
                while (true) {
                    val prevCommon = common
                    common += if (k == 0) splits[0] else separator + splits[k]
                    if (!paths2.all { it.startsWith(common + separator) || it == common } ) return prevCommon
                    if (++k == n) return common
                }
            }
        }
}