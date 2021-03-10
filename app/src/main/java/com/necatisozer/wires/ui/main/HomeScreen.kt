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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.necatisozer.wires.R
import com.necatisozer.wires.domain.model.User
import java.util.Locale

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
) {
    val userState: State<User?> = homeViewModel.user.collectAsState(initial = null)
    val viewState: State<HomeViewState> = homeViewModel.viewState.collectAsState()

    if (userState.value != null) {
        navController.navigate("chat")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = viewState.value.nickname,
            onValueChange = homeViewModel::onNicknameChange,
            label = { Text(stringResource(R.string.home_nickname_label)) },
            singleLine = true,
            isError = viewState.value.showNicknameError,
        )
        AnimatedVisibility(visible = viewState.value.showNicknameError) {
            Text(
                text = stringResource(id = R.string.home_nickname_error),
                color = MaterialTheme.colors.error,
                fontSize = 12.sp,
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = homeViewModel::onEnterChatClick) {
            Text(text = stringResource(id = R.string.home_enter_chat_button).toUpperCase(Locale.getDefault()))
        }
    }
}
