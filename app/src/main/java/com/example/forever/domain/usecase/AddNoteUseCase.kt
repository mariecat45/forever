package com.example.forever.domain.usecase

import com.example.forever.domain.model.Note
import com.example.forever.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(text: String, ownerId: String) {
        val note = Note(
            id = 0L, // 0L, чтобы Room сгенерировал новый ID
            ownerId = ownerId,
            text = text,
            createdAt = System.currentTimeMillis(),
            updatedAt = null
        )
        repository.insertNote(note)
    }
}