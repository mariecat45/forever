package com.example.forever.presentation.ui.screens

sealed class Screen {
    object Welcome : Screen()
    object NameInput : Screen()
    object Home : Screen()
}