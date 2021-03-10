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
import com.necatisozer.wires.domain.localdatasources.ThemeLocalDataSource
import com.necatisozer.wires.domain.model.Theme
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import splitties.preferences.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemePreferences @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : Preferences("theme"), ThemeLocalDataSource {
    private var themePref = StringPref("theme", Theme.SYSTEM.name)

    override val theme: Flow<Theme>
        get() = themePref.valueFlow()
            .map { Theme.valueOf(it) }
            .flowOn(ioDispatcher)

    override suspend fun setTheme(theme: Theme) = withContext(ioDispatcher) {
        themePref.value = theme.name
    }
}
