package com.jungha.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<E : BaseEvent, S : BaseState> : ViewModel() {
    protected abstract val events: Channel<E>
    abstract val state : StateFlow<S>

    protected abstract suspend fun reduce(state:S, event:E) : S
    fun onEvent(event: E) = viewModelScope.launch {
        events.send(event)
    }

}