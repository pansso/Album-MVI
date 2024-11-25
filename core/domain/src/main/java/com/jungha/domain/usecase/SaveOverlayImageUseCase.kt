package com.jungha.domain.usecase

import com.jungha.domain.repository.MediaRepository
import javax.inject.Inject

class SaveOverlayImageUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(imageId:String,overlayImg:String): Result<Unit> {
        return mediaRepository.saveOverlayPhoto(imageId,overlayImg)
    }
}