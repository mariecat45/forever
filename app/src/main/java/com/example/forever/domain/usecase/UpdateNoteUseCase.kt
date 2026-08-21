package com.example.forever.domain.usecase

import com.example.forever.domain.model.Note
import com.example.forever.domain.repository.NoteRepository

class UpdateNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        repository.updateNote(note)
    }
}