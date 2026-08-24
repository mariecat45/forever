package com.example.forever.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forever.domain.model.Attachment
import com.example.forever.domain.model.Note
import com.example.forever.presentation.ui.screens.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.forever.domain.usecase.AddNoteUseCase
import kotlinx.coroutines.launch
import com.example.forever.domain.usecase.*

class MainViewModel(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getAttachmentsUseCase: GetAttachmentsForNoteUseCase,
    private val addAttachmentUseCase: AddAttachmentUseCase,
    private val deleteAttachmentUseCase: DeleteAttachmentUseCase,
    private val observeOnboardingUseCase: ObserveOnboardingUseCase,
    private val completeOnboardingUseCase: CompleteOnboardingUseCase,
    private val observeUserNameUseCase: ObserveUserNameUseCase,
    private val saveUserNameUseCase: SaveUserNameUseCase,
    private val restoreNoteUseCase: RestoreNoteUseCase
) : ViewModel() {

    private val _currentScreen = MutableStateFlow<Screen>(Screen.Loading)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private val _currentAttachments = MutableStateFlow<List<Attachment>>(emptyList())
    val currentAttachments: StateFlow<List<Attachment>> = _currentAttachments.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        checkOnboardingStatus()
        loadUserName()
        loadNotes()
    }

    private fun checkOnboardingStatus() {
        viewModelScope.launch {
            observeOnboardingUseCase().collect { completed ->
                _currentScreen.value = if (completed) Screen.Home else Screen.Welcome
            }
        }
    }

    private fun loadUserName() {
        viewModelScope.launch {
            observeUserNameUseCase().collect { name ->
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
            completeOnboardingUseCase(name)
            _userName.value = name
            _currentScreen.value = Screen.Home
        }
    }

    fun updateUserName(newName: String) {
        viewModelScope.launch {
            saveUserNameUseCase(newName)
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
            getAllNotesUseCase().collect { notesList ->
                _notes.value = notesList
                _isLoading.value = false
            }
        }
    }

    fun addNote(text: String) {
        viewModelScope.launch {
            addNoteUseCase(text, _userName.value)
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            deleteNoteUseCase(noteId)
        }
    }

    fun updateNote(note: Note, newText: String) {
        viewModelScope.launch {
            val updatedNote = note.copy(
                text = newText,
                updatedAt = System.currentTimeMillis()
            )
            updateNoteUseCase(updatedNote)
        }
    }

    // РАБОТА С ВЛОЖЕНИЯМИ
    fun loadAttachmentsForNote(noteId: Long) {
        viewModelScope.launch {
            getAttachmentsUseCase(noteId).collect { attachments ->
                _currentAttachments.value = attachments
            }
        }
    }

    fun addAttachment(noteId: Long, filePath: String, fileName: String, fileType: String = "IMAGE") {
        viewModelScope.launch {
            val attachment = Attachment(
                noteId = noteId,
                filePath = filePath,
                fileName = fileName,
                fileType = fileType
            )
            addAttachmentUseCase(attachment)
        }
    }

    fun deleteAttachment(attachmentId: Long) {
        viewModelScope.launch {
            deleteAttachmentUseCase(attachmentId)
        }
    }

    fun restoreNote(note: Note) {
        viewModelScope.launch {
            restoreNoteUseCase(note)  // ← Теперь просто делегируем
        }
    }
}
