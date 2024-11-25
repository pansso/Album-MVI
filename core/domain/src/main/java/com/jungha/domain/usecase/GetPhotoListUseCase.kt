package com.jungha.domain.usecase

import com.jungha.domain.model.Album
import com.jungha.domain.model.Photo
import com.jungha.domain.repository.MediaRepository
import javax.inject.Inject

class GetPhotoListUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(albumId: String): List<Photo> {
        return mediaRepository.getPhotos(albumId)
    }
}