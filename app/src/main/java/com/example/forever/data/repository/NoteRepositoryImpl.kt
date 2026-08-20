package com.example.forever.data.repository

import com.example.forever.data.source.database.AttachmentDao
import com.example.forever.data.source.database.NoteDao
import com.example.forever.data.source.local.AttachmentEntity
import com.example.forever.data.source.local.NoteEntity
import com.example.forever.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val noteDao: NoteDao,
    private val attachmentDao: AttachmentDao
) : NoteRepository {

    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteDao.getAllNotes()
    }

    override suspend fun insertNote(note: NoteEntity) {
        noteDao.insertNote(note)
    }

    override suspend fun updateNote(note: NoteEntity) {
        noteDao.updateNote(note)
    }

    override suspend fun deleteNote(noteId: Long) {
        noteDao.deleteNote(noteId)
    }

    override fun getAttachmentsForNote(noteId: Long): Flow<List<AttachmentEntity>> {
        return attachmentDao.getAttachmentsForNote(noteId)
    }

    override suspend fun insertAttachment(attachment: AttachmentEntity) {
        attachmentDao.insertAttachment(attachment)
    }

    override suspend fun deleteAttachment(attachmentId: Long) {
        attachmentDao.deleteAttachment(attachmentId)
    }

    override suspend fun getRandomNote(): NoteEntity? = noteDao.getRandomNote()
}