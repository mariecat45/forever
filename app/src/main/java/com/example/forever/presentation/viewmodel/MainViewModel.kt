package com.example.forever.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forever.data.repository.UserPreferencesRepository
import com.example.forever.data.source.local.AttachmentEntity
import com.example.forever.data.source.local.NoteEntity
import com.example.forever.presentation.ui.screens.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.forever.domain.repository.NoteRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: NoteRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _currentScreen = MutableStateFlow<Screen>(Screen.Loading)
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
        checkOnboardingStatus() // ← ДОБАВИТЬ
        loadUserName()
        loadNotes()
    }

    private fun checkOnboardingStatus() {
        viewModelScope.launch {
            userPreferencesRepository.isOnboardingCompleted.collect { completed ->
                _currentScreen.value = if (completed) Screen.Home else Screen.Welcome
            }
        }
    }

    private fun loadUserName() {
        viewModelScope.launch {
            userPreferencesRepository.userName.collect { name ->
                _userName.value = name
            }
        }
    }

    // НАВИГАЦИЯ
    fun onStartClicked() {
        _currentScreen.value = Screen.NameInput
    }

    fun onNameSaved(name: String) {
        viewModelScope.launch {
            userPreferencesRepository.setUserName(name)           // ← ДОБАВИТЬ
            userPreferencesRepository.setOnboardingCompleted(true) // ← ДОБАВИТЬ
            _userName.value = name
            _currentScreen.value = Screen.Home
        }
    }

    // Изменить имя (для экрана настроек)
    fun updateUserName(newName: String) {
        viewModelScope.launch {
            userPreferencesRepository.setUserName(newName)
            _userName.value = newName
        }
    }

    fun onSettingsClicked() {
        _currentScreen.value = Screen.Settings
    }

    fun onNoteClicked(noteId: Long) {
        _currentScreen.value = Screen.NoteDetail(noteId)
    }

    fun onBackToHome() {
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
