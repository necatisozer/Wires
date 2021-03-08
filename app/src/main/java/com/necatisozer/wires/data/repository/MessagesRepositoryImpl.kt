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
package com.necatisozer.wires.data.repository

import com.necatisozer.wires.data.repository.localdatasources.MessagesLocalDataSource
import com.necatisozer.wires.data.repository.remotedatasources.PreviousMessagesRemoteDataSource
import com.necatisozer.wires.domain.model.Message
import com.necatisozer.wires.domain.repositories.MessagesRepository
import com.necatisozer.wires.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.datetime.Clock
import java.util.UUID
import javax.inject.Inject

class MessagesRepositoryImpl @Inject constructor(
    private val messagesLocalDataSource: MessagesLocalDataSource,
    private val previousMessagesRemoteDataSource: PreviousMessagesRemoteDataSource,
    private val userRepository: UserRepository,
) : MessagesRepository {
    override val messages: Flow<List<Message>>
        get() = messagesLocalDataSource.messages
            .onStart {
                val messages = messagesLocalDataSource.messages.first()

                if (messages.isEmpty()) {
                    val previousMessages = previousMessagesRemoteDataSource.getPreviousMessages()
                    messagesLocalDataSource.addMessages(previousMessages)
                }
            }

    override suspend fun sendMessage(text: String) {
        val user = userRepository.getUser()
            ?: throw IllegalStateException("User must be created before sending message")

        val message = Message(
            id = UUID.randomUUID().toString(),
            text = text,
            timestamp = Clock.System.now().epochSeconds,
            user = user,
        )

        messagesLocalDataSource.addMessage(message)
    }
}
