package com.jungha.album

import com.jungha.base.BaseEvent
import com.jungha.base.BaseState
import com.jungha.domain.model.Album

internal sealed interface AlbumEvent : BaseEvent {
    data object LoadAlbums : AlbumEvent
    data class SelectAlbum(val albumId: String) : AlbumEvent
}

internal data class AlbumState(
    val loadAlbums: List<Album> = emptyList(),
    val isLoading: Boolean = false,
    val selectAlbum: Album= Album("","",0),
    val error: String? = null
) : BaseState