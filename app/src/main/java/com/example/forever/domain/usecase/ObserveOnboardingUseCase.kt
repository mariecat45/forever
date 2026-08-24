package com.example.forever.domain.usecase

import com.example.forever.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class ObserveOnboardingUseCase(private val repository: UserPreferencesRepository) {
    operator fun invoke(): Flow<Boolean> = repository.isOnboardingCompleted
}