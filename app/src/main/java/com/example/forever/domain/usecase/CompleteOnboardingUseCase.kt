package com.example.forever.domain.usecase

import com.example.forever.domain.repository.UserPreferencesRepository

class CompleteOnboardingUseCase(private val repository: UserPreferencesRepository) {
    suspend operator fun invoke(userName: String) {
        require(userName.isNotBlank()) { "Имя не может быть пустым" }
        repository.setUserName(userName.trim())
        repository.setOnboardingCompleted(true)
    }
}