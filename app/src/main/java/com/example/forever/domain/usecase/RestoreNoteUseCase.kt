package com.example.forever.domain.usecase

import com.example.forever.domain.model.Note
import com.example.forever.domain.repository.NoteRepository

class RestoreNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        // Создаём копию с id=0, чтобы БД сгенерировала новый ID
        val restoredNote = note.copy(
            id = 0L,
            updatedAt = System.currentTimeMillis()
        )
        repository.insertNote(restoredNote)
    }
}