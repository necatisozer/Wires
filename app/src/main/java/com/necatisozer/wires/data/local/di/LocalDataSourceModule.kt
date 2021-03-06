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
package com.necatisozer.wires.data.local.di

import com.necatisozer.wires.data.local.memory.MessagesInMemoryData
import com.necatisozer.wires.data.local.preferences.ThemePreferences
import com.necatisozer.wires.data.local.preferences.UserPreferences
import com.necatisozer.wires.data.repository.localdatasources.MessagesLocalDataSource
import com.necatisozer.wires.data.repository.localdatasources.UserLocalDataSource
import com.necatisozer.wires.domain.localdatasources.ThemeLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataSourceModule {
    @get:Binds
    val UserPreferences.userPreferences: UserLocalDataSource

    @get:Binds
    val MessagesInMemoryData.messagesInMemoryData: MessagesLocalDataSource

    @get:Binds
    val ThemePreferences.themePreferences: ThemeLocalDataSource
}
