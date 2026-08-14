package com.example.forever.domain.model

class Attachment(
    val id: Long = 0,
    val noteId: Long,          // ← привязка к заметке
    val filePath: String,      // ← относительный путь, например "attachments/photo_123.jpg"
    val fileType: String,      // "IMAGE", "AUDIO", "VIDEO", "FILE"
    val fileName: String,      // "мой_файл.jpg"
    val createdAt: Long = System.currentTimeMillis()
)