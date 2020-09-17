package com.example.dima.imagesorter.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log

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


fun getListOfImageBuckets(parentContext : Context) {
    // which image properties are we querying
    val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATE_TAKEN)

    // content:// style URI for the "primary" external storage volume
    val images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    // Make the query.
    val cur = parentContext.contentResolver.query(images,
            projection, // Selection arguments (none)
            null        // Ordering
            , null, null
    )// Which columns to return
    // Which rows to return (all rows)

    Log.i("ListingImages", " query count=" + cur.getCount())

    if (cur.moveToFirst()) {
        var bucket: String
        var date: String
        val bucketColumn = cur.getColumnIndex(
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        val dateColumn = cur.getColumnIndex(
                MediaStore.Images.Media.DATE_TAKEN)

        do {
            // Get the field values
            bucket = cur.getString(bucketColumn)
            date = cur.getString(dateColumn)

            // Do something with the values.
            Log.i("ListingImages", " bucket=" + bucket
                    + "  date_taken=" + date)
        } while (cur.moveToNext())

    }
}

