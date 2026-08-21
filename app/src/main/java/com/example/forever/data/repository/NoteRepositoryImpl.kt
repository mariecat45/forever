package com.example.forever.data.repository

import com.example.forever.data.source.database.AttachmentDao
import com.example.forever.data.source.database.NoteDao
import com.example.forever.data.source.local.AttachmentEntity
import com.example.forever.data.source.local.NoteEntity
import com.example.forever.domain.model.Attachment
import com.example.forever.domain.model.Note
import com.example.forever.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val noteDao: NoteDao,
    private val attachmentDao: AttachmentDao
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> =
        noteDao.getAllNotes().map { list -> list.map { it.toDomain() } }

    override suspend fun insertNote(note: Note): Long =
        noteDao.insertNote(note.toEntity())

    override suspend fun updateNote(note: Note) =
        noteDao.updateNote(note.toEntity())

    override suspend fun deleteNote(noteId: Long) =
        noteDao.deleteNote(noteId)

    override fun getAttachmentsForNote(noteId: Long): Flow<List<Attachment>> =
        attachmentDao.getAttachmentsForNote(noteId).map { list -> list.map { it.toDomain() } }

    override suspend fun insertAttachment(attachment: Attachment) {
        attachmentDao.insertAttachment(attachment.toEntity())
    }

    override suspend fun deleteAttachment(attachmentId: Long) =
        attachmentDao.deleteAttachment(attachmentId)

    override suspend fun getRandomNote(): Note? =
        noteDao.getRandomNote()?.toDomain()
}

private fun NoteEntity.toDomain(): Note =
    Note(id = id, ownerId = ownerId, text = text, createdAt = createdAt, updatedAt = updatedAt)

private fun Note.toEntity(): NoteEntity =
    NoteEntity(id = id, ownerId = ownerId, text = text, createdAt = createdAt, updatedAt = updatedAt)

private fun AttachmentEntity.toDomain(): Attachment =
    Attachment(id = id, noteId = noteId, filePath = filePath, fileType = fileType, fileName = fileName, createdAt = createdAt)

private fun Attachment.toEntity(): AttachmentEntity =
    AttachmentEntity(id = id, noteId = noteId, filePath = filePath, fileType = fileType, fileName = fileName, createdAt = createdAt)