package com.example.forever.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val isOnboardingCompleted: Flow<Boolean>
    val userName: Flow<String>
    suspend fun setOnboardingCompleted(completed: Boolean)
    suspend fun setUserName(name: String)
}