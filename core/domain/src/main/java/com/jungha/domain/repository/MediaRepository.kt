package com.jungha.domain.repository

import com.jungha.domain.model.Album
import com.jungha.domain.model.Photo

interface MediaRepository {
    suspend fun getAlbums(): List<Album>
    suspend fun getPhotos(albumId: String): List<Photo>
    suspend fun saveOverlayPhoto(photoId: String, overlayImg: String): Result<Unit>

}