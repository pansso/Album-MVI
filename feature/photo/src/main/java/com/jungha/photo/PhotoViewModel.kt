package com.jungha.photo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jungha.base.BaseViewModel
import com.jungha.domain.model.Album
import com.jungha.domain.usecase.GetPhotoListUseCase
import com.jungha.photo.navigation.PhotoRoute
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
internal class PhotoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPhotoListUseCase: GetPhotoListUseCase,
) : BaseViewModel<PhotoEvent, PhotoState>() {

    override val events: Channel<PhotoEvent> = Channel()
    override val state: StateFlow<PhotoState> = events.receiveAsFlow()
        .runningFold(PhotoState(), ::reduce)
        .stateIn(viewModelScope, SharingStarted.Eagerly, PhotoState())

    init {
        val serializedAlbum = savedStateHandle.get<String>(PhotoRoute.KEY_INTENT_ALBUM)
        val album = Gson().fromJson(serializedAlbum,Album::class.java)
        onEvent(PhotoEvent.LoadPhoto(album))
    }

    override suspend fun reduce(state: PhotoState, event: PhotoEvent): PhotoState {
        return when (event) {
            is PhotoEvent.LoadPhoto -> {
                state.copy(
                    photoList = getPhotoListUseCase.invoke(event.album.id).also { it.map { Timber.d("sjh $it") } }
                )
            }

            is PhotoEvent.SelectPhoto -> {
                state.copy(

                )
            }
        }
    }

}