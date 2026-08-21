package com.example.forever.domain.usecase

import com.example.forever.domain.repository.NoteRepository

class DeleteAttachmentUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(attachmentId: Long) =
        repository.deleteAttachment(attachmentId)
}