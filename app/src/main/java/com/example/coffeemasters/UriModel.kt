package com.example.coffeemasters

import android.content.Context
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.net.URI

class UriModel(private val context: Context) {
    fun readBytes(uriString: String) : ByteArray? {
        val uri = Uri.parse(uriString)
        val blob = context.contentResolver.openInputStream(uri)?.use {
            it.readBytes()
        }
        Log.d("urimodel","blob size: ${blob?.size} bytes")
        return blob
    }

    companion object {
        fun readBytes(context: Context, uriString: String) : ByteArray? {
            val uri = Uri.parse(uriString)
            val blob = context.contentResolver.openInputStream(uri)?.use {
                it.readBytes()
            }
            return blob
        }

        fun writeBlobToFile(context: Context, blob: ByteArray, filename: String) : File {
            val file = File(context.filesDir, filename)
            FileOutputStream(file).use {
                it.write(blob)
            }
            return file
        }
    }

    fun createFileUri(blob: ByteArray, filename: String) : URI {
        val file = File(this.context.filesDir, filename)
        FileOutputStream(file).use {
            it.write(blob)
        }
        Log.d("urimodel","file created: ${file.absolutePath}")
        return file.toURI()
    }
}