package com.example.forever.data.source.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "attachments",
    foreignKeys = [
        ForeignKey(
            entity = NoteEntity::class,
            parentColumns = ["id"],
            childColumns = ["noteId"],
            onDelete = ForeignKey.CASCADE // если удаляем заметку, удаляем и вложения
        )
    ]
)
data class AttachmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val noteId: Long,          // ← привязка к заметке
    val filePath: String,      // ← относительный путь, например "attachments/photo_123.jpg"
    val fileType: String,      // "IMAGE", "AUDIO", "VIDEO", "FILE"
    val fileName: String,      // "мой_файл.jpg"
    val createdAt: Long = System.currentTimeMillis()
)