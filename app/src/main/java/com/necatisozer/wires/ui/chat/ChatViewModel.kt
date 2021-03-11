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
package com.necatisozer.wires.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.necatisozer.wires.domain.localdatasources.ThemeLocalDataSource
import com.necatisozer.wires.domain.model.Theme
import com.necatisozer.wires.domain.repositories.MessagesRepository
import com.necatisozer.wires.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val messagesRepository: MessagesRepository,
    private val themeLocalDataSource: ThemeLocalDataSource,
) : ViewModel() {
    val viewState = combine(
        userRepository.user,
        messagesRepository.messages,
        themeLocalDataSource.theme,
    ) { user, messages, theme ->
        ChatViewState(
            user = user,
            messages = messages,
            theme = theme,
        )
    }

    fun deleteUserAndMessages() = viewModelScope.launch {
        messagesRepository.clearMessages()
        userRepository.deleteUser()
    }

    fun setTheme(theme: Theme) = viewModelScope.launch {
        themeLocalDataSource.setTheme(theme)
    }

    fun sendMessage(text: String) = viewModelScope.launch {
        messagesRepository.sendMessage(text)
    }
}
