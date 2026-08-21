package com.example.forever.domain.usecase

import com.example.forever.domain.model.Attachment
import com.example.forever.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetAttachmentsForNoteUseCase(private val repository: NoteRepository) {
    operator fun invoke(noteId: Long): Flow<List<Attachment>> =
        repository.getAttachmentsForNote(noteId)
}