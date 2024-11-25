package com.jungha.data.impl

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.jungha.data.mapper.*
import com.jungha.data.model.AlbumEntity
import com.jungha.data.model.PhotoEntity
import com.jungha.domain.model.Album
import com.jungha.domain.model.Photo
import com.jungha.domain.repository.MediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

internal class MediaRepositoryImpl @Inject constructor(
    private val contentResolver: ContentResolver,
) : MediaRepository {
    override suspend fun getAlbums(): List<Album> = withContext(Dispatchers.IO) {
        val projection = arrayOf(
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA
        )
        val selection = "${MediaStore.Images.Media.MIME_TYPE} LIKE ?"
        val selectionArgs = arrayOf("image/%")
        val sortOrder = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} ASC"

        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        ) ?: return@withContext emptyList()

        val albumMap = mutableMapOf<String, AlbumEntity>()

        cursor.use {
            while (it.moveToNext()) {
                val albumId =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID))
                val albumName =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                val imageUri = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

                val albumEntity = albumMap.getOrPut(albumId) {
                    AlbumEntity(
                        id = albumId,
                        name = albumName,
                        thumbnailUri = imageUri?.let { uri -> Uri.parse(uri) }
                    )
                }

                albumEntity.photoCount++
            }
        }


        return@withContext albumMap.values.map { it.toModel() }
    }

    override suspend fun getPhotos(albumId: String): List<Photo> = withContext(Dispatchers.IO) {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
        )

        val selection = "${MediaStore.Images.Media.BUCKET_ID} = ?"
        val selectionArgs = arrayOf(albumId)
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        ) ?: return@withContext emptyList()

        val photoList = mutableListOf<PhotoEntity>()

        cursor.use {
            while (it.moveToNext()) {
                val photoId = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                val photoName =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                val photoPath = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                val albumName = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))

                photoList.add(
                    PhotoEntity(
                        id = photoId,
                        name = photoName,
                        contentUri = photoPath?.let { uri-> Uri.parse(uri) },
                        albumName = albumName
                    )
                )
            }
        }

        return@withContext photoList.map { it.toModel() }
    }

    override suspend fun saveOverlayPhoto(photoId: String, overlayImg: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}