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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.necatisozer.wires.core.extension.hiltViewModel
import com.necatisozer.wires.ui.chat.ChatScreen
import com.necatisozer.wires.ui.main.HomeScreen
import com.necatisozer.wires.ui.theme.WiresTheme

@Composable
fun WiresApp() {
    val navController = rememberNavController()

    WiresTheme {
        Surface(color = MaterialTheme.colors.background) {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { backStackEntry ->
                    HomeScreen(
                        hiltViewModel(backStackEntry),
                        navController,
                    )
                }
                composable("chat") { ChatScreen(navController) }
            }
        }
    }
}
