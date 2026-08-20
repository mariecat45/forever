package com.example.forever.domain.repository

import com.example.forever.data.source.local.AttachmentEntity
import com.example.forever.data.source.local.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<NoteEntity>>
    suspend fun insertNote(note: NoteEntity)
    suspend fun updateNote(note: NoteEntity)
    suspend fun deleteNote(noteId: Long)
    fun getAttachmentsForNote(noteId: Long): Flow<List<AttachmentEntity>>
    suspend fun insertAttachment(attachment: AttachmentEntity)
    suspend fun deleteAttachment(attachmentId: Long)

    suspend fun getRandomNote(): NoteEntity?
}