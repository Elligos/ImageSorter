package com.example.dima.imagesorter.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

fun getListOfImagesPath(parentContext : Context) : ArrayList<String> {
    val uri: Uri
    val cursor: Cursor
    val column_index_data: Int
    val column_index_folder_name: Int
    val listOfAllImages = ArrayList<String>()
    var absolutePathOfImage: String? = null
    uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
    parentContext.contentResolver
    cursor = parentContext.contentResolver.query(uri, projection, null, null, null)

    column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
    column_index_folder_name = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
    while (cursor.moveToNext()) {
        absolutePathOfImage = cursor.getString(column_index_data)

        listOfAllImages.add(absolutePathOfImage)
    }
    return listOfAllImages
}