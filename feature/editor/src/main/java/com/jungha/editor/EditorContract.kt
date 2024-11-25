package com.jungha.editor

import com.jungha.base.BaseEvent
import com.jungha.base.BaseState
import com.jungha.domain.model.Album
import com.jungha.domain.model.Photo

internal sealed interface EditorEvent : BaseEvent {
    data class Initialize(val photo: Photo): EditorEvent
    data class SelectSvgImage(val svgPath: String) : EditorEvent
    data object OverlayButtonToggle : EditorEvent
    data object SaveOverlayImg : EditorEvent
}

internal data class EditorState(
    val selectedPhoto:Photo? = null,
    val selectSvgImage: String? = null,
    val svgPath : List<String> = emptyList(),
    val errorMessage: String? = null
) : BaseState