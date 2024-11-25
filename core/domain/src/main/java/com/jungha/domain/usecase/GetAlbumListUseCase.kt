package com.jungha.domain.usecase

import com.jungha.domain.model.Album
import com.jungha.domain.repository.MediaRepository
import javax.inject.Inject

class GetAlbumListUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
){
    suspend operator fun invoke(): List<Album> {
        return mediaRepository.getAlbums()
    }
}