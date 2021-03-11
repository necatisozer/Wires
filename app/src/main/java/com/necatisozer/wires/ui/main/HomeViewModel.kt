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
package com.necatisozer.wires.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.necatisozer.wires.domain.model.User
import com.necatisozer.wires.domain.repositories.UserRepository
import com.necatisozer.wires.util.validator.NicknameValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val nicknameValidator: NicknameValidator,
) : ViewModel() {
    val user: Flow<User?> = userRepository.user

    private val _viewState = MutableStateFlow(HomeViewState())
    val viewState = _viewState.asStateFlow()

    fun onNicknameChange(nickname: String) {
        _viewState.value = _viewState.value.copy(
            nickname = nickname,
            showNicknameError = false,
        )
    }

    fun onEnterChatClick() {
        if (isNickNameValid) {
            createUser()
        } else {
            _viewState.value = _viewState.value.copy(
                showNicknameError = true,
            )
        }
    }

    private fun createUser() = viewModelScope.launch {
        userRepository.createUser(viewState.value.nickname)
    }

    private val isNickNameValid get() = nicknameValidator.isValid(viewState.value.nickname)
}
