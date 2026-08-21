package com.example.forever.domain.usecase

import com.example.forever.domain.model.Attachment
import com.example.forever.domain.repository.NoteRepository

class AddAttachmentUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(attachment: Attachment) =
        repository.insertAttachment(attachment)
}