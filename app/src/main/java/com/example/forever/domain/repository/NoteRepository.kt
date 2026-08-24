package com.example.forever.domain.repository

import com.example.forever.domain.model.Attachment
import com.example.forever.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(noteId: Long)
    fun getAttachmentsForNote(noteId: Long): Flow<List<Attachment>>
    suspend fun insertAttachment(attachment: Attachment)
    suspend fun deleteAttachment(attachmentId: Long)

    suspend fun getRandomNote(): Note?
}