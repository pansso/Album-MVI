package com.jungha.data.model

import android.net.Uri

data class AlbumEntity(
    val id: String,
    val name: String,
    var photoCount: Int = 0,
    val thumbnailUri: Uri? = null
)