package com.example.forever.domain.usecase

import com.example.forever.domain.repository.NoteRepository

class DeleteNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(noteId: Long) = repository.deleteNote(noteId)
}