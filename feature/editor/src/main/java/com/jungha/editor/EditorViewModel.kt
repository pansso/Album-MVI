package com.jungha.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jungha.base.BaseViewModel
import com.jungha.domain.model.Album
import com.jungha.domain.model.Photo
import com.jungha.domain.usecase.GetSvgListUseCase
import com.jungha.editor.navigator.EditorRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class EditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSvgListUseCase: GetSvgListUseCase,
) : BaseViewModel<EditorEvent, EditorState>() {

    override val events: Channel<EditorEvent> = Channel()
    override val state: StateFlow<EditorState> = events.receiveAsFlow()
        .runningFold(EditorState(), ::reduce)
        .stateIn(viewModelScope, SharingStarted.Eagerly, EditorState())

    init {
        val serializedPhoto = savedStateHandle.get<String>(EditorRoute.KEY_INTENT_PHOTO)
        val photo = Gson().fromJson(serializedPhoto, Photo::class.java)
        onEvent(EditorEvent.Initialize(photo))
    }

    override suspend fun reduce(state: EditorState, event: EditorEvent): EditorState {
        return when (event) {
            is EditorEvent.Initialize -> {
                state.copy(
                    selectedPhoto = event.photo,
                    svgPath = getSvgListUseCase.invoke()
                ).also { Timber.d("sjh result =$it") }
            }

            is EditorEvent.OverlayButtonToggle -> {
                state.copy()
            }

            is EditorEvent.SaveOverlayImg -> {
                state.copy()
            }

            is EditorEvent.SelectSvgImage -> {
                state.copy(
                    selectSvgImage = event.svgPath
                )
            }
        }
    }


}