/*
 * Copyright 2021 Necati Sozer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    override fun addMessage(message: Message) = mutateMessages {
        add(message)
    }

    override fun addMessages(messages: List<Message>) = mutateMessages {
        addAll(messages)
    }

    private fun mutateMessages(mutation: MutableList<Message>.() -> Unit) {
        _messages.value = _messages.value
            .toMutableList()
            .apply(mutation)
            .toList()
    }
}
