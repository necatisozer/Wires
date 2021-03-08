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
package com.necatisozer.wires.data.repository

import com.necatisozer.wires.domain.localdatasources.UserLocalDataSource
import com.necatisozer.wires.domain.model.User
import com.necatisozer.wires.domain.repositories.UserRepository
import java.util.UUID
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
) : UserRepository {

    override suspend fun createUser(nickname: String): User {
        return User(
            id = UUID.randomUUID().toString(),
            avatarURL = "https://ssl.gstatic.com/images/branding/product/1x/avatar_square_blue_512dp.png",
            nickname = nickname,
        ).also {
            userLocalDataSource.setUser(it)
        }
    }

    override suspend fun getUser(): User? = userLocalDataSource.getUser()

    override suspend fun deleteUser() = userLocalDataSource.removeUser()
}
