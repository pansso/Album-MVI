package com.jungha.data.model

import android.net.Uri

data class PhotoEntity(
    val id: String,
    val contentUri: Uri? = null,
    val name: String,
    val albumName: String
)
