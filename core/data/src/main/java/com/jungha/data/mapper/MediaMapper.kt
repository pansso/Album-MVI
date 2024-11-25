package com.jungha.data.mapper

import com.jungha.data.model.AlbumEntity
import com.jungha.data.model.PhotoEntity
import com.jungha.domain.model.Album
import com.jungha.domain.model.Photo

internal fun AlbumEntity.toModel(): Album {
    return Album(
        id = this.id,
        name = this.name,
        photoCount = this.photoCount,
        thumbnail = this.thumbnailUri?.toString()
    )
}

internal fun PhotoEntity.toModel(): Photo {
    return Photo(
        id = this.id,
        name = this.name,
        albumName = this.albumName,
        image = this.contentUri.toString()
    )
}
