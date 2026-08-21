package com.example.forever.domain.usecase

import com.example.forever.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class ObserveUserNameUseCase(private val repository: UserPreferencesRepository) {
    operator fun invoke(): Flow<String> = repository.userName
}