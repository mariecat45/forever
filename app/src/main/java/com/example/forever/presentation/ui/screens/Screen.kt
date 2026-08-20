package com.example.forever.presentation.ui.screens

sealed class Screen {
    object Loading : Screen()
    object Welcome : Screen()
    object NameInput : Screen()
    object Home : Screen()
    object Settings : Screen()
    data class NoteDetail(val noteId: Long) : Screen()
}