package com.example.forever.domain.usecase

import com.example.forever.domain.repository.UserPreferencesRepository

class SaveUserNameUseCase(private val repository: UserPreferencesRepository) {
    suspend operator fun invoke(name: String) {
        require(name.isNotBlank()) { "Имя не может быть пустым" }
        repository.setUserName(name.trim())
    }
}