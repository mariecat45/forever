package com.example.forever.data.source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.forever.data.source.local.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    // Получить все заметки (Flow автоматически обновляет список, если в БД что-то изменится)
    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    // Добавить новую заметку
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    // Удалить заметку
    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNote(noteId: Long)
}