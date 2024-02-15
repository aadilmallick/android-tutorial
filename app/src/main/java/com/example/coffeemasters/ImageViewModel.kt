package com.example.coffeemasters

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ImageViewModel: ViewModel() {
    private var uri: Uri =  mutableStateOf(Uri.EMPTY).value

    fun setUri(uri: Uri) {
        this.uri = uri
    }

    fun getUri(): Uri {
        return uri
    }
}