package com.example.coffeemasters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.net.URI

class PhotoCompressionWorker(
    private val appContext: Context,
    private val workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams)  {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            // we will replace this later on when passing in data to worker
            val stringUri = workerParams.inputData.getString(KEY_CONTENT_URI)
            val compressionThresholdInBytes = workerParams.inputData.getLong(KEY_COMPRESSION_THRESHOLD, 0L)

            // if reading bytes fails, returns result.failure()
            val blob = appContext.contentResolver.openInputStream(Uri.parse(stringUri))?.use {
                it.readBytes()
            } ?: return@withContext Result.failure()

            val bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.size)
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)

            val compressedBlob = outputStream.toByteArray()
            val file = UriModel.writeBlobToFile(appContext, compressedBlob, "${workerParams.id}-compressed.jpg")

            Result.success(
                workDataOf(
                    KEY_RESULT_PATH to file.absolutePath
                )
            )
        }
    }

    // these are temporary placeholder for data we'll pass into our worker and use
    companion object {
        val KEY_CONTENT_URI = "KEY_CONTENT_URI"
        val KEY_COMPRESSION_THRESHOLD = "KEY_COMPRESSION_THRESHOLD"
        val KEY_RESULT_PATH = "KEY_RESULT_PATH"
    }
}