package com.necatisozer.wires.data.local.di

import com.necatisozer.wires.data.local.preferences.UserPreferences
import com.necatisozer.wires.domain.UserLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataSourceModule {
    @get:Binds
    val UserPreferences.userPreferences: UserLocalDataSource
}