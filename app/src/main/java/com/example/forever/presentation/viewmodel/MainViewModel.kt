package com.example.forever.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forever.data.source.local.AttachmentEntity
import com.example.forever.data.source.local.NoteEntity
import com.example.forever.presentation.ui.screens.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.forever.domain.repository.NoteRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: NoteRepository // ← ВОТ ЭТО ДОБАВИЛИ! Koin сам подставит сюда репозиторий
) : ViewModel() {

    private val _currentScreen = MutableStateFlow<Screen>(Screen.Welcome)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    // Данные заметок
    private val _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
    val notes: StateFlow<List<NoteEntity>> = _notes.asStateFlow()

    private val _currentAttachments = MutableStateFlow<List<AttachmentEntity>>(emptyList())
    val currentAttachments: StateFlow<List<AttachmentEntity>> = _currentAttachments.asStateFlow()

    // Состояние загрузки
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // При создании ViewModel загружаем все заметки
    init {
        loadNotes()
    }

    // НАВИГАЦИЯ
    fun onStartClicked() {
        _currentScreen.value = Screen.NameInput
    }

    fun onNameSaved(name: String) {
        _userName.value = name
        _currentScreen.value = Screen.Home
    }

    // РАБОТА С ЗАМЕТКАМИ

    private fun loadNotes() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllNotes().collect { notesList ->
                _notes.value = notesList
                _isLoading.value = false
            }
        }
    }

    fun addNote(text: String) {
        viewModelScope.launch {
            val note = NoteEntity(
                ownerId = _userName.value, // Привязываем к текущему пользователю
                text = text,
                createdAt = System.currentTimeMillis(),
                updatedAt = null
            )
            repository.insertNote(note)
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            repository.deleteNote(noteId) // CASCADE сам удалит вложения!
        }
    }

    fun updateNote(note: NoteEntity, newText: String) {
        viewModelScope.launch {
            val updatedNote = note.copy(
                text = newText,
                updatedAt = System.currentTimeMillis()
            )
            repository.updateNote(updatedNote)
        }
    }

    // РАБОТА С ВЛОЖЕНИЯМИ

    fun loadAttachmentsForNote(noteId: Long) {
        viewModelScope.launch {
            repository.getAttachmentsForNote(noteId).collect { attachments ->
                _currentAttachments.value = attachments
            }
        }
    }

    fun addAttachment(noteId: Long, filePath: String, fileName: String, fileType: String = "IMAGE") {
        viewModelScope.launch {
            val attachment = AttachmentEntity(
                noteId = noteId,
                filePath = filePath,
                fileName = fileName,
                fileType = fileType
            )
            repository.insertAttachment(attachment)
        }
    }

    fun deleteAttachment(attachmentId: Long) {
        viewModelScope.launch {
            repository.deleteAttachment(attachmentId)
        }
    }
}
