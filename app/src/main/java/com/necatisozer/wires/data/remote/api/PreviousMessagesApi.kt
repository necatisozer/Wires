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
package com.necatisozer.wires.data.remote.api

import com.necatisozer.wires.core.di.IoDispatcher
import com.necatisozer.wires.data.remote.api.model.MessagesResponse
import com.necatisozer.wires.data.repository.remotedatasources.PreviousMessagesRemoteDataSource
import com.necatisozer.wires.domain.model.Message
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.net.URL
import javax.inject.Inject

class PreviousMessagesApi @Inject constructor(
    private val json: Json,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : PreviousMessagesRemoteDataSource {
    override suspend fun getPreviousMessages(): List<Message> = withContext(ioDispatcher) {
        val messagesResponse = json.decodeFromString(
            deserializer = MessagesResponse.serializer(),
            string = previousMessagesJsonString,
        )
        return@withContext messagesResponse.messages
    }

    private val previousMessagesJsonString get() = URL(PREVIOUS_MESSAGES_URL).readText()

    companion object {
        private const val PREVIOUS_MESSAGES_URL =
            "https://jsonblob.com/api/jsonBlob/62455171-0fb1-11eb-9f83-ffcd873e5c3a"
    }
}
