package com.example.forever.data.source.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.forever.data.source.local.AttachmentEntity
import com.example.forever.data.source.local.NoteEntity

@Database(
    entities = [NoteEntity::class, AttachmentEntity::class],
    version = 1,
    exportSchema = false // Отключаем экспорт схемы, чтобы не было лишних папок при разработке
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun attachmentDao(): AttachmentDao
}