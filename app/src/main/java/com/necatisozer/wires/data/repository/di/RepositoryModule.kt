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
package com.necatisozer.wires.data.repository.di

import com.necatisozer.wires.data.repository.MessagesRepositoryImpl
import com.necatisozer.wires.data.repository.UserRepositoryImpl
import com.necatisozer.wires.domain.repositories.MessagesRepository
import com.necatisozer.wires.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @get:Binds
    val UserRepositoryImpl.userRepositoryImpl: UserRepository

    @get:Binds
    val MessagesRepositoryImpl.messagesRepositoryImpl: MessagesRepository
}
