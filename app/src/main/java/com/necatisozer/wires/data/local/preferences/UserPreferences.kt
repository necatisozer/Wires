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
package com.necatisozer.wires.data.local.preferences

import com.necatisozer.wires.core.di.IoDispatcher
import com.necatisozer.wires.domain.localdatasources.UserLocalDataSource
import com.necatisozer.wires.domain.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import splitties.preferences.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    private val json: Json,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : Preferences("user"), UserLocalDataSource {
    private var userPref = StringOrNullPref("user")

    override suspend fun getUser(): User? = withContext(ioDispatcher) {
        userPref.value?.let { encodedString ->
            json.decodeFromString(User.serializer(), encodedString)
        }
    }

    override suspend fun setUser(user: User) = withContext(ioDispatcher) {
        userPref.value = json.encodeToString(User.serializer(), user)
    }

    override suspend fun removeUser() = withContext(ioDispatcher) {
        userPref.value = null
    }
}
