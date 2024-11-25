package com.jungha.photo

import com.jungha.base.BaseEvent
import com.jungha.base.BaseState
import com.jungha.domain.model.Album
import com.jungha.domain.model.Photo

internal sealed interface PhotoEvent : BaseEvent {
    data class SelectPhoto(val photo: Photo) : PhotoEvent
    data class LoadPhoto(val album: Album) : PhotoEvent
}

internal data class PhotoState(
    val photoList: List<Photo>? = null,
    val selectPhoto: Photo = Photo("", "", "")
) : BaseState