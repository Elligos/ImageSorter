package com.example.dima.imagesorter.providers

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
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
        removeDublicates(folders)
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
        removeDublicates(folderPaths)
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
//        val imagePattern = ArrayList<String>()
//        imagePattern.add("jpg")
//        imagePattern.add("png")
//        var isImage = false


        Log.i("Search in folder:", " $folderPath")

        if (folder.isDirectory) {
            listFile = folder.listFiles()
            for (image in listFile) {
//                if(image.extension == "") continue
//                isImage=false
//                for(pattern in imagePattern){
//                    if(image.extension.toLowerCase() == pattern) isImage=true
//                }
//                if(!isImage) continue
                if(!isImage(image.extension)) continue
                f.add(image.absolutePath)
                Log.i("Found file:", " ${image.absolutePath}")
            }
        }
        return f
    }

    fun isImage(extension : String) : Boolean
    {
        val imagePattern = ArrayList<String>()
        imagePattern.add("jpg")
        imagePattern.add("png")
        var isImage = false
        for(pattern in imagePattern){
            if(extension.toLowerCase(Locale.ROOT) == pattern) isImage=true
        }
        return isImage
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
                removeDublicates(folders)
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
                removeDublicates(folderPaths)
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

            private fun removeDublicates(list : ArrayList<String>)
            {
                var set = HashSet(list)
                list.clear()
                list.addAll(set)
            }
        }
}