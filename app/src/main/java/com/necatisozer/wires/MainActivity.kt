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
package com.necatisozer.wires

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.view.WindowCompat
import androidx.lifecycle.asLiveData
import com.necatisozer.wires.domain.model.Theme.DARK
import com.necatisozer.wires.domain.model.Theme.LIGHT
import com.necatisozer.wires.ui.WiresApp
import com.necatisozer.wires.ui.WiresViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val wiresViewModel: WiresViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        observeTheme()
        setContent { WiresApp() }
    }

    private fun observeTheme() {
        wiresViewModel.theme.asLiveData().observe(this) { theme ->
            AppCompatDelegate.setDefaultNightMode(
                when (theme) {
                    LIGHT -> MODE_NIGHT_NO
                    DARK -> MODE_NIGHT_YES
                    else -> MODE_NIGHT_FOLLOW_SYSTEM
                }
            )
        }
    }
}
