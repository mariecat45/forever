package com.example.forever.data.source.local
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes",
    indices = [Index("ownerId")]
)

data class NoteEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val ownerId: String,
    val text: String?,
    val createdAt: Long,
    val updatedAt: Long?
)