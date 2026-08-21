package com.example.forever

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import com.example.forever.presentation.ui.screens.NameInputScreen
import com.example.forever.presentation.ui.screens.Screen
import com.example.forever.presentation.ui.screens.WelcomeScreen
import com.example.forever.presentation.viewmodel.MainViewModel
import com.example.forever.ui.theme.ForeverTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.forever.presentation.ui.screens.HomeScreen
import com.example.forever.presentation.ui.screens.LoadingScreen
import com.example.forever.presentation.ui.screens.NoteDetailScreen
import com.example.forever.presentation.ui.screens.SettingsScreen
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = koinViewModel()

            val currentScreen by viewModel.currentScreen.collectAsState()
            val userName by viewModel.userName.collectAsState()

            ForeverTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    when (val screen = currentScreen) {
                        Screen.Loading -> LoadingScreen()
                        Screen.Welcome -> WelcomeScreen(
                            onStartClick = viewModel::onStartClicked
                        )
                        Screen.NameInput -> NameInputScreen(
                            onNameSaved = viewModel::onNameSaved
                        )
                        Screen.Home -> HomeScreen(
                            viewModel = viewModel,
                            onSettingsClick = viewModel::onSettingsClicked
                        )
                        Screen.Settings -> SettingsScreen(
                            viewModel = viewModel,
                            onBackClick = viewModel::onBackToHome
                        )
                        is Screen.NoteDetail -> NoteDetailScreen(
                            viewModel = viewModel,
                            noteId = screen.noteId,
                            onBackClick = viewModel::onBackToHome
                        )
                    }
                }
            }
        }
    }
}