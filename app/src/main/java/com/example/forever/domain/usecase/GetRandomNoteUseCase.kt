package com.example.forever.domain.usecase

import com.example.forever.domain.model.Note
import com.example.forever.domain.repository.NoteRepository

class GetRandomNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(): Note? = repository.getRandomNote()
}