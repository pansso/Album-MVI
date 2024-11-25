package com.jungha.album

import androidx.lifecycle.viewModelScope
import com.jungha.base.BaseViewModel
import com.jungha.domain.usecase.GetAlbumListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class AlbumViewModel @Inject constructor(
    private val getMediaSourceUseCase: GetAlbumListUseCase
) : BaseViewModel<AlbumEvent, AlbumState>() {

    override val events: Channel<AlbumEvent> = Channel<AlbumEvent>()
    override val state: StateFlow<AlbumState> = events.receiveAsFlow()
        .runningFold(AlbumState(), ::reduce)
        .stateIn(viewModelScope, SharingStarted.Eagerly, AlbumState())

    init {
        onEvent(AlbumEvent.LoadAlbums)
    }

    override suspend fun reduce(state: AlbumState, event: AlbumEvent): AlbumState {
        return when(event){
            is AlbumEvent.LoadAlbums -> {
                state.copy(
                     loadAlbums = getMediaSourceUseCase()
                )
            }
            is AlbumEvent.SelectAlbum -> {
                state.copy(

                )
            }
        }
    }
}