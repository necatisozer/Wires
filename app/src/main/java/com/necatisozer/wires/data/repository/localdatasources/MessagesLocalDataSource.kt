package com.necatisozer.wires.data.repository.localdatasources

import com.necatisozer.wires.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessagesLocalDataSource {
    val messages: Flow<List<Message>>
    fun addMessage(message: Message)
}