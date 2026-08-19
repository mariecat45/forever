package com.example.forever.data.source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.forever.data.source.local.AttachmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AttachmentDao {

    // Получить все вложения для конкретной заметки
    @Query("SELECT * FROM attachments WHERE noteId = :noteId ORDER BY createdAt DESC")
    fun getAttachmentsForNote(noteId: Long): Flow<List<AttachmentEntity>>

    // Добавить вложение
    @Insert
    suspend fun insertAttachment(attachment: AttachmentEntity): Long

    // Удалить вложение
    @Query("DELETE FROM attachments WHERE id = :attachmentId")
    suspend fun deleteAttachment(attachmentId: Long)

    // Получить все вложения (может пригодиться для галереи)
    @Query("SELECT * FROM attachments ORDER BY createdAt DESC")
    fun getAllAttachments(): Flow<List<AttachmentEntity>>
}