package com.necatisozer.wires.data.local.memory

import com.necatisozer.wires.data.repository.localdatasources.MessagesLocalDataSource
import com.necatisozer.wires.domain.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessagesInMemoryData @Inject constructor() : MessagesLocalDataSource {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())

    override val messages: Flow<List<Message>>
        get() = _messages.asStateFlow()

    override fun addMessage(message: Message) {
        _messages.value = _messages.value
            .toMutableList()
            .apply { add(message) }
            .toList()
    }

}