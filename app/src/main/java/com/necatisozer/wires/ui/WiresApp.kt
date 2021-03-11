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
package com.necatisozer.wires.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.necatisozer.wires.domain.model.Theme
import com.necatisozer.wires.domain.model.Theme.SYSTEM
import com.necatisozer.wires.domain.model.isDarkTheme
import com.necatisozer.wires.ui.chat.ChatScreen
import com.necatisozer.wires.ui.main.HomeScreen
import com.necatisozer.wires.ui.theme.WiresTheme
import com.necatisozer.wires.util.compose.hiltViewModel
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@Composable
fun WiresApp() {
    val navController = rememberNavController()
    val wiresViewModel: WiresViewModel = viewModel()

    val themeState: State<Theme> = wiresViewModel.theme.collectAsState(initial = SYSTEM)

    ProvideWindowInsets(consumeWindowInsets = false) {
        WiresTheme(darkTheme = themeState.value.isDarkTheme()) {
            Surface(color = MaterialTheme.colors.background) {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { backStackEntry ->
                        HomeScreen(
                            hiltViewModel(backStackEntry),
                            navController,
                        )
                    }
                    composable("chat") { backStackEntry ->
                        ChatScreen(
                            hiltViewModel(backStackEntry),
                            navController,
                        )
                    }
                }
            }
        }
    }
}
