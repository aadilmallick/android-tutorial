package com.example.coffeemasters

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import java.util.Calendar

data class ImageData(
    val id: Long,
    val displayName: String,
    val dateTaken: Long,
    val contentUri: Uri
)

class ContentProviderPractice(private var context: Context) {
    private val images = mutableListOf<ImageData>()
    fun mediaStorePractice() {
        // projection is the fields you want back
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN)

        // get data that represents yesterday
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val milliseconds = calendar.timeInMillis

        val selection = "${MediaStore.Images.Media.DATE_TAKEN} >= ?"
        val selectionArgs = arrayOf(milliseconds.toString())
        context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
"${MediaStore.Images.Media.DATE_TAKEN} DESC"
        )?.use { cursor ->
            // get columns
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dateTakenColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
            // move through our data
            while (cursor.moveToNext()) {
                // parse our data according to each column data type
                val id = cursor.getLong(idColumn)
                val displayName = cursor.getString(displayNameColumn)
                val dateTaken = cursor.getLong(dateTakenColumn)
                // use the data
                val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                images.add(ImageData(id, displayName, dateTaken, uri))
            }
        }

    }

    fun getImages(): List<ImageData> {
        return images
    }
}