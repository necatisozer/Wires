package com.necatisozer.wires.data.local.preferences

import com.necatisozer.wires.domain.UserLocalDataSource
import splitties.preferences.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor() : Preferences("user"), UserLocalDataSource {
    override var nickname: String? by StringOrNullPref("nickname")
}