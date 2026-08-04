package com.example.forever

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.forever.presentation.ui.screens.NameInputScreen
import com.example.forever.presentation.ui.screens.Screen
import com.example.forever.presentation.ui.screens.WelcomeScreen
import com.example.forever.presentation.viewmodel.MainViewModel
import com.example.forever.ui.theme.ForeverTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel()

            val currentScreen by viewModel.currentScreen.collectAsState()
            val userName by viewModel.userName.collectAsState()

            ForeverTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    when (currentScreen) {
                        Screen.Welcome -> WelcomeScreen(
                            onStartClick = viewModel::onStartClicked
                        )
                        Screen.NameInput -> NameInputScreen(
                            onNameSaved = viewModel::onNameSaved
                        )
                        Screen.Home -> Text("Экран Home в разработке")
                    }
                }
            }
        }
    }
}